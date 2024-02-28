package com.weather.sensor.service.impl;

import com.weather.sensor.entity.*;
import com.weather.sensor.enums.SensorStatusEnum;
import com.weather.sensor.exception.NotFoundException;
import com.weather.sensor.mapper.SensorMapper;
import com.weather.sensor.model.AlertResponse;
import com.weather.sensor.model.CO2Statistics;
import com.weather.sensor.model.MeasurementResponse;
import com.weather.sensor.repository.*;
import com.weather.sensor.service.SensorService;
import com.weather.sensor.model.SensorMeasurement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final SensorStatusRepository sensorStatusRepository;
    private final MeasurementRepository measurementRepository;
    private final AlertRepository alertRepository;
    private final MetricsRepository metricsRepository;
    private final Long co2AlertLevel = 2000L;
    private final int alertCountLevel = 3;
    private final ZoneOffset zoneOffset = ZoneOffset.UTC;

    @Override
    public Sensor findSensorByUuid(UUID sensorUuid) {
        Sensor sensor = sensorRepository.findBySensorUuid(sensorUuid);
        if(Objects.isNull(sensor)){
            log.info("Sensor with UUID {} not found!", sensorUuid);
            throw new NotFoundException(String.format("Sensor with UUID %s not found!",sensorUuid));
        }
        return sensor;
    }

    @Override
    public void collectMeasurements(String uuid, SensorMeasurement sensorMeasurement) {
        Sensor sensor = findSensorByUuid(UUID.fromString(uuid));
        Measurement measurement = SensorMapper.mapSensorMeasurementToMeasurementEntity(sensor, sensorMeasurement);
        measurement = measurementRepository.save(measurement);
        SensorStatus sensorStatus = findSensorStatusBySensorId(sensor.getId());
        if(Objects.isNull(sensorStatus)){
            createAndSaveSensorStatus(sensor, measurement);
        }else{
            updateSensorStatus(sensorStatus, measurement);
        }
    }

    @Override
    public com.weather.sensor.model.SensorStatus getSensorStatusByUuid(String uuid) {
        Sensor sensor = findSensorByUuid(UUID.fromString(uuid));
        SensorStatus sensorStatus = findSensorStatusBySensorId(sensor.getId());
        if(Objects.isNull(sensorStatus)){
            log.info("Sensor status with UUID {} not found!", uuid);
            throw new NotFoundException(String.format("Sensor status with UUID %s not found!", uuid));
        }
        com.weather.sensor.model.SensorStatus status = new com.weather.sensor.model.SensorStatus();
        status.setStatus(com.weather.sensor.model.SensorStatus.StatusEnum.fromValue(sensorStatus.getStatus().name()));
        return status;
    }

    @Override
    public List<AlertResponse> listSensorAlerts(String uuid) {
        List<AlertResponse> alertResponseList = new ArrayList<>();
        Sensor sensor = findSensorByUuid(UUID.fromString(uuid));
        List<Alert> alerts = alertRepository.findAllBySensorId(sensor.getId());
        alerts.forEach(alert -> {
            AlertResponse alertResponse = new AlertResponse();
            alertResponse.setStartTime(alert.getStartedAt().atOffset(zoneOffset));
            alertResponse.setEndTime(OffsetDateTime.from(alert.getEndedAt().atOffset(zoneOffset)));
            alertResponse.setMeasurements(getMeasurementResponses(alert));
            alertResponseList.add(alertResponse);
        });
        return alertResponseList;
    }

    @Override
    public CO2Statistics getSensorMetrics(String uuid) {
        Sensor sensor = findSensorByUuid(UUID.fromString(uuid));
        CO2Statistics metrics = new CO2Statistics();
        SensorDataLast30Days metric = metricsRepository.getById(sensor.getId());
        if(metric == null){
            log.info("Sensor metrics with UUID {} not found!", uuid);
            throw new NotFoundException(String.format("Sensor metrics with UUID %s not found!", uuid));
        }
        metrics.setAvgLast30Days(metric.getAvgLast30Days().doubleValue());
        metrics.setMaxLast30Days(metric.getMaxLast30Days());
        return metrics;
    }

    private List<MeasurementResponse> getMeasurementResponses(Alert alert) {
        List<Measurement> measurements = measurementRepository
                .findTop3BySensorIdAndDateRangeOrderedNative(alert.getSensor().getId(),
                        alert.getStartedAt(), alert.getEndedAt());
        List<MeasurementResponse> measurementResponses = new ArrayList<>();
        measurements.forEach(measurement -> {
            MeasurementResponse measurementResponse = new MeasurementResponse();
            measurementResponse.setCo2(measurement.getCo2Amount());
            measurementResponse.setId(measurement.getSensorUuid());
            measurementResponses.add(measurementResponse);
        });
        return measurementResponses;
    }

    private void updateSensorStatus(SensorStatus sensorStatus, Measurement measurement) {
        if((SensorStatusEnum.OK.equals(sensorStatus.getStatus()) && measurement.getCo2Amount() <= co2AlertLevel)
        || (SensorStatusEnum.ALERT.equals(sensorStatus.getStatus()) && measurement.getCo2Amount() > co2AlertLevel
        && sensorStatus.getWarnCount() >= alertCountLevel)){
            return;
        }
        if(SensorStatusEnum.OK.equals(sensorStatus.getStatus())){
            changeToWarnStatus(sensorStatus, measurement);
        }else if(SensorStatusEnum.WARN.equals(sensorStatus.getStatus())){
            if(measurement.getCo2Amount() > co2AlertLevel){
                sensorStatus.setWarnCount(sensorStatus.getWarnCount() + 1);
                if(sensorStatus.getWarnCount() == alertCountLevel){
                    sensorStatus.setStatus(SensorStatusEnum.ALERT);
                    createAndSaveNewAlert(sensorStatus, measurement);
                }
            }else{
                changeToOkStatus(sensorStatus);
            }
        }else{
            if(measurement.getCo2Amount() > co2AlertLevel){
                sensorStatus.setWarnCount(alertCountLevel);
            }else{
                sensorStatus.setWarnCount(sensorStatus.getWarnCount() - 1);
                if(sensorStatus.getWarnCount() == 0){
                    changeToOkStatus(sensorStatus);
                    updateAlertEndTime(measurement);
                }
            }
        }
        sensorStatusRepository.save(sensorStatus);
    }

    private void updateAlertEndTime(Measurement measurement) {
        Optional<Alert> optionalAlert = alertRepository.findTopBySensorUuidOrderByIdDesc(measurement.getSensorUuid());
        optionalAlert.ifPresentOrElse(
                alert -> {
                    alert.setEndedAt(measurement.getCreatedAt());
                    alertRepository.save(alert);
                },
                () -> { throw new NotFoundException("Alert not found for sensor UUID: " + measurement.getSensorUuid()); } // If not present, throw exception
        );
    }

    private void createAndSaveNewAlert(SensorStatus sensorStatus, Measurement measurement) {
        alertRepository.save(Alert.builder()
                .endedAt(measurement.getCreatedAt())
                .startedAt(sensorStatus.getStartedAt())
                .sensor(sensorStatus.getSensor())
                .createdAt(LocalDateTime.now())
                .sensorUuid(sensorStatus.getSensorUuid())
                .build());
    }


    private SensorStatus findSensorStatusBySensorId(Long sensorId) {
        return sensorStatusRepository.findBySensorId(sensorId);
    }

    private void createAndSaveSensorStatus(Sensor sensor, Measurement measurement) {
        SensorStatus sensorStatus = SensorStatus.builder()
                .sensor(sensor)
                .sensorUuid(sensor.getSensorUuid())
                .build();
        if(measurement.getCo2Amount() <= co2AlertLevel){
            changeToOkStatus(sensorStatus);
        }else{
            changeToWarnStatus(sensorStatus, measurement);
        }
        sensorStatusRepository.save(sensorStatus);
    }

    private void changeToOkStatus(SensorStatus sensorStatus) {
        sensorStatus.setStatus(SensorStatusEnum.OK);
        sensorStatus.setWarnCount(0);
        sensorStatus.setStartedAt(LocalDateTime.now());
    }

    private void changeToWarnStatus(SensorStatus sensorStatus, Measurement measurement) {
        sensorStatus.setStatus(SensorStatusEnum.WARN);
        sensorStatus.setWarnCount(1);
        sensorStatus.setStartedAt(measurement.getCreatedAt());
    }
}
