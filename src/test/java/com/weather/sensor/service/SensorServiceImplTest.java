package com.weather.sensor.service;

import com.weather.sensor.entity.*;
import com.weather.sensor.enums.SensorStatusEnum;
import com.weather.sensor.exception.NotFoundException;
import com.weather.sensor.model.AlertResponse;
import com.weather.sensor.model.CO2Statistics;
import com.weather.sensor.model.SensorMeasurement;
import com.weather.sensor.repository.*;
import com.weather.sensor.service.impl.SensorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class SensorServiceImplTest {

    @Mock
    private SensorRepository sensorRepository;
    @Mock
    private SensorStatusRepository sensorStatusRepository;
    @Mock
    private MeasurementRepository measurementRepository;
    @Mock
    private AlertRepository alertRepository;
    @Mock
    private MetricsRepository metricsRepository;

    @InjectMocks
    private SensorServiceImpl sensorService;

    // Common variables used across tests
    private UUID sensorUuid = UUID.randomUUID();
    private Sensor sensor;
    private SensorMeasurement sensorMeasurement;

    @BeforeEach
    void setUp() {
        sensor = new Sensor();
        sensor.setId(1L);
        sensor.setSensorUuid(sensorUuid);
        sensorMeasurement = new SensorMeasurement();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenSensorExists_findSensorByUuid_ShouldReturnSensor() {
        when(sensorRepository.findBySensorUuid(sensorUuid)).thenReturn(sensor);

        Sensor foundSensor = sensorService.findSensorByUuid(sensorUuid);

        assertEquals(sensor, foundSensor);
    }

    @Test
    void whenSensorDoesNotExist_findSensorByUuid_ShouldThrowNotFoundException() {
        when(sensorRepository.findBySensorUuid(sensorUuid)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> sensorService.findSensorByUuid(sensorUuid));
    }

    @Test
    void whenCollectMeasurementsIsCalled_WithValidData_ShouldProcessSuccessfully() {
        when(sensorRepository.findBySensorUuid(any(UUID.class))).thenReturn(sensor);
        // Assuming a simplistic scenario for saving measurement
        sensorMeasurement.setCo2(300L);
        sensorMeasurement.setTime(LocalDateTime.now().atOffset(ZoneOffset.UTC));
        when(measurementRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        sensorService.collectMeasurements(sensorUuid.toString(), sensorMeasurement);

        verify(measurementRepository).save(any());
    }

    @Test
    void whenCollectMeasurementsIsCalled_WithMoreCo2_ShouldProcessSuccessfully() {
        when(sensorRepository.findBySensorUuid(any(UUID.class))).thenReturn(sensor);
        // Assuming a simplistic scenario for saving measurement
        sensorMeasurement.setCo2(3000L);
        sensorMeasurement.setTime(LocalDateTime.now().atOffset(ZoneOffset.UTC));

        when(measurementRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        sensorService.collectMeasurements(sensorUuid.toString(), sensorMeasurement);

        verify(measurementRepository).save(any());
    }

    @Test
    void whenCollectMeasurementsIsCalled_WithLessCo2_forOKSensorState() {
        when(sensorRepository.findBySensorUuid(any(UUID.class))).thenReturn(sensor);
        // Assuming a simplistic scenario for saving measurement
        sensorMeasurement.setCo2(1000L);
        sensorMeasurement.setTime(LocalDateTime.now().atOffset(ZoneOffset.UTC));
        SensorStatus sensorStatus = new SensorStatus();
        sensorStatus.setStatus(SensorStatusEnum.OK);
        sensorStatus.setWarnCount(0);
        when(sensorStatusRepository.findBySensorId(sensor.getId()))
                .thenReturn(sensorStatus);
        when(measurementRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        sensorService.collectMeasurements(sensorUuid.toString(), sensorMeasurement);

        verify(measurementRepository).save(any());
    }

    @Test
    void whenCollectMeasurementsIsCalled_WithLessCo2_forWarnSensorState() {
        when(sensorRepository.findBySensorUuid(any(UUID.class))).thenReturn(sensor);
        // Assuming a simplistic scenario for saving measurement
        sensorMeasurement.setCo2(1000L);
        sensorMeasurement.setTime(LocalDateTime.now().atOffset(ZoneOffset.UTC));
        SensorStatus sensorStatus = new SensorStatus();
        sensorStatus.setStatus(SensorStatusEnum.WARN);
        sensorStatus.setWarnCount(0);
        when(sensorStatusRepository.findBySensorId(sensor.getId()))
                .thenReturn(sensorStatus);
        when(measurementRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        sensorService.collectMeasurements(sensorUuid.toString(), sensorMeasurement);

        verify(measurementRepository).save(any());
    }

    @Test
    void whenCollectMeasurementsIsCalled_WithMoreCo2_forWarnSensorState() {
        when(sensorRepository.findBySensorUuid(any(UUID.class))).thenReturn(sensor);
        // Assuming a simplistic scenario for saving measurement
        sensorMeasurement.setCo2(3000L);
        sensorMeasurement.setTime(LocalDateTime.now().atOffset(ZoneOffset.UTC));
        SensorStatus sensorStatus = new SensorStatus();
        sensorStatus.setStatus(SensorStatusEnum.WARN);
        sensorStatus.setWarnCount(2);
        when(sensorStatusRepository.findBySensorId(sensor.getId()))
                .thenReturn(sensorStatus);
        when(measurementRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        sensorService.collectMeasurements(sensorUuid.toString(), sensorMeasurement);

        verify(measurementRepository).save(any());
    }

    @Test
    void whenCollectMeasurementsIsCalled_WithMoreCo2_forAlertSensorState() {
        when(sensorRepository.findBySensorUuid(any(UUID.class))).thenReturn(sensor);
        // Assuming a simplistic scenario for saving measurement
        sensorMeasurement.setCo2(3000L);
        sensorMeasurement.setTime(LocalDateTime.now().atOffset(ZoneOffset.UTC));
        SensorStatus sensorStatus = new SensorStatus();
        sensorStatus.setStatus(SensorStatusEnum.ALERT);
        sensorStatus.setWarnCount(2);
        when(sensorStatusRepository.findBySensorId(sensor.getId()))
                .thenReturn(sensorStatus);
        when(measurementRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        sensorService.collectMeasurements(sensorUuid.toString(), sensorMeasurement);

        verify(measurementRepository).save(any());
    }

    @Test
    void whenCollectMeasurementsIsCalled_throwNotFound_forAlertSensorState() {
        when(sensorRepository.findBySensorUuid(any(UUID.class))).thenReturn(sensor);
        // Assuming a simplistic scenario for saving measurement
        sensorMeasurement.setCo2(1000L);
        sensorMeasurement.setTime(LocalDateTime.now().atOffset(ZoneOffset.UTC));
        SensorStatus sensorStatus = new SensorStatus();
        sensorStatus.setStatus(SensorStatusEnum.ALERT);
        sensorStatus.setWarnCount(1);
        when(sensorStatusRepository.findBySensorId(sensor.getId()))
                .thenReturn(sensorStatus);
        when(measurementRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        assertThrows(NotFoundException.class, () -> sensorService.collectMeasurements(sensorUuid.toString(), sensorMeasurement));
    }

    @Test
    void whenCollectMeasurementsIsCalled_WithLessCo2_forAlertSensorState() {
        when(sensorRepository.findBySensorUuid(any(UUID.class))).thenReturn(sensor);
        // Assuming a simplistic scenario for saving measurement
        sensorMeasurement.setCo2(1000L);
        sensorMeasurement.setTime(LocalDateTime.now().atOffset(ZoneOffset.UTC));
        SensorStatus sensorStatus = new SensorStatus();
        sensorStatus.setStatus(SensorStatusEnum.ALERT);
        sensorStatus.setWarnCount(1);
        Alert alert = new Alert();
        alert.setSensorUuid(sensor.getSensorUuid());
        alert.setEndedAt(LocalDateTime.now().minusDays(1));
        alert.setEndedAt(LocalDateTime.now());
        alert.setCreatedAt(LocalDateTime.now());
        when(sensorStatusRepository.findBySensorId(sensor.getId()))
                .thenReturn(sensorStatus);
        when(measurementRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(alertRepository.findTopBySensorUuidOrderByIdDesc(sensor.getSensorUuid())).thenReturn(Optional.of(alert));
        sensorService.collectMeasurements(sensorUuid.toString(), sensorMeasurement);

        verify(alertRepository).save(any());
    }

    @Test
    void whenCollectMeasurementsIsCalled_WithMoreCo2_forExistedSensorState() {
        when(sensorRepository.findBySensorUuid(any(UUID.class))).thenReturn(sensor);
        // Assuming a simplistic scenario for saving measurement
        sensorMeasurement.setCo2(3000L);
        sensorMeasurement.setTime(LocalDateTime.now().atOffset(ZoneOffset.UTC));
        SensorStatus sensorStatus = new SensorStatus();
        sensorStatus.setStatus(SensorStatusEnum.OK);
        sensorStatus.setWarnCount(0);
        when(sensorStatusRepository.findBySensorId(sensor.getId()))
                .thenReturn(sensorStatus);
        when(measurementRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        sensorService.collectMeasurements(sensorUuid.toString(), sensorMeasurement);

        verify(measurementRepository).save(any());
        // Add more verifications as necessary for the business logic
    }

    @Test
    void whenCollectMeasurementsIsCalled_AndSensorNotFound_ShouldThrowNotFoundException() {
        when(sensorRepository.findBySensorUuid(any(UUID.class))).thenReturn(null);

        assertThrows(NotFoundException.class, () -> sensorService.collectMeasurements(sensorUuid.toString(), sensorMeasurement));
    }

    @Test
    void whenSensorStatusExists_getSensorStatusByUuid_ShouldReturnStatus() {
        SensorStatus sensorStatus = new SensorStatus();
        sensorStatus.setStatus(SensorStatusEnum.OK);
        when(sensorRepository.findBySensorUuid(sensorUuid)).thenReturn(sensor);
        when(sensorStatusRepository.findBySensorId(sensor.getId())).thenReturn(sensorStatus);

        com.weather.sensor.model.SensorStatus resultStatus = sensorService.getSensorStatusByUuid(sensorUuid.toString());

        assertNotNull(resultStatus);
        assertEquals(com.weather.sensor.model.SensorStatus.StatusEnum.OK, resultStatus.getStatus());
    }

    @Test
    void whenSensorStatusDoesNotExist_getSensorStatusByUuid_ShouldThrowNotFoundException() {
        when(sensorRepository.findBySensorUuid(sensorUuid)).thenReturn(sensor);
        when(sensorStatusRepository.findBySensorId(sensor.getId())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> sensorService.getSensorStatusByUuid(sensorUuid.toString()));
    }

    @Test
    void whenAlertsExist_listSensorAlerts_ShouldReturnAlertsList() {
        Alert alert = new Alert();
        alert.setEndedAt(LocalDateTime.now());
        alert.setStartedAt(LocalDateTime.now().minusDays(1));
        alert.setSensor(sensor);
        alert.setCreatedAt(LocalDateTime.now());
        alert.setSensorUuid(sensorUuid);
        List<Alert> alerts = Collections.singletonList(alert);
        when(sensorRepository.findBySensorUuid(sensorUuid)).thenReturn(sensor);
        when(alertRepository.findAllBySensorId(sensor.getId())).thenReturn(alerts);
        Measurement measurement = new Measurement();
        measurement.setCo2Amount(2002L);
        measurement.setSensorUuid(sensor.getSensorUuid());
        measurement.setCreatedAt(LocalDateTime.now());
        when(measurementRepository.findTop3BySensorIdAndDateRangeOrderedNative(sensor.getId(),
                alert.getStartedAt(), alert.getEndedAt()))
                .thenReturn(List.of(measurement));
        List<AlertResponse> response = sensorService.listSensorAlerts(sensorUuid.toString());

        assertFalse(response.isEmpty());
        assertEquals(alerts.size(), response.size());
        assertEquals(measurement.getCo2Amount(), response.get(0).getMeasurements().get(0).getCo2());
    }

    @Test
    void whenNoAlertsExist_listSensorAlerts_ShouldReturnEmptyList() {
        when(sensorRepository.findBySensorUuid(sensorUuid)).thenReturn(sensor);
        when(alertRepository.findAllBySensorId(sensor.getId())).thenReturn(Collections.emptyList());

        List<AlertResponse> response = sensorService.listSensorAlerts(sensorUuid.toString());

        assertTrue(response.isEmpty());
    }

    @Test
    void whenMetricsExist_getSensorMetrics_ShouldReturnMetrics() {
        SensorDataLast30Days metrics = new SensorDataLast30Days();
        metrics.setAvgLast30Days(BigDecimal.valueOf(1000));
        metrics.setMaxLast30Days(2000L);
        when(sensorRepository.findBySensorUuid(sensorUuid)).thenReturn(sensor);
        when(metricsRepository.getById(sensor.getId())).thenReturn(metrics);

        CO2Statistics metricsResponse = sensorService.getSensorMetrics(sensorUuid.toString());

        assertNotNull(metricsResponse);
        assertEquals(1000.0, metricsResponse.getAvgLast30Days());
        assertEquals(2000, metricsResponse.getMaxLast30Days());
    }

    @Test
    void whenMetricsNotExist_getSensorMetrics_ShouldThrowException() {
        when(sensorRepository.findBySensorUuid(sensorUuid)).thenReturn(sensor);
        when(metricsRepository.getById(sensor.getId())).thenReturn(null);
        assertThrows(NotFoundException.class, ()-> sensorService.getSensorMetrics(sensorUuid.toString()));
    }

}
