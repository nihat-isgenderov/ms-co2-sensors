package com.weather.sensor.controller;

import com.weather.sensor.api.SensorApi;
import com.weather.sensor.model.AlertResponse;
import com.weather.sensor.model.CO2Statistics;
import com.weather.sensor.model.SensorMeasurement;
import com.weather.sensor.model.SensorStatus;
import com.weather.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SensorController implements SensorApi {
    private final SensorService sensorService;

    @Override
    public ResponseEntity<Void> collectMeasurements(String uuid, SensorMeasurement sensorMeasurement){
        log.debug("Collecting measurements for sensor with uuid: {}", uuid);
        sensorService.collectMeasurements(uuid, sensorMeasurement);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SensorStatus> getSensorStatus(String uuid){
        log.debug("Get sensor status for uuid: {}", uuid);
        return new ResponseEntity<>(sensorService.getSensorStatusByUuid(uuid), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AlertResponse>> listSensorAlerts(String uuid){
        log.debug("Get sensor alerts for uuid: {}", uuid);
        return new ResponseEntity<>(sensorService.listSensorAlerts(uuid), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CO2Statistics> getSensorMetrics(String uuid){
        log.debug("Get sensor metrics for uuid: {}", uuid);
        return new ResponseEntity<>(sensorService.getSensorMetrics(uuid), HttpStatus.OK);
    }
}
