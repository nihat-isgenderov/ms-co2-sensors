package com.weather.sensor.mapper;

import com.weather.sensor.entity.Measurement;
import com.weather.sensor.entity.Sensor;
import com.weather.sensor.model.SensorMeasurement;

import java.time.LocalDateTime;

public class SensorMapper {
    public static Measurement mapSensorMeasurementToMeasurementEntity(Sensor sensor, SensorMeasurement sensorMeasurement) {
        return Measurement.builder()
                .sensorUuid(sensor.getSensorUuid())
                .co2Amount(sensorMeasurement.getCo2())
                .sensor(sensor)
                .createdAt(sensorMeasurement.getTime().toLocalDateTime())
                .build();
    }
}
