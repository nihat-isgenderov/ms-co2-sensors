package com.weather.sensor.service;

import com.weather.sensor.entity.Sensor;
import com.weather.sensor.model.AlertResponse;
import com.weather.sensor.model.CO2Statistics;
import com.weather.sensor.model.SensorMeasurement;
import com.weather.sensor.model.SensorStatus;
import java.util.List;
import java.util.UUID;

/**
 * Defines the operations available for interacting with sensor data.
 */
public interface SensorService {

    /**
     * Finds a sensor by its unique identifier.
     *
     * @param sensorUuid the UUID of the sensor
     * @return the sensor entity if found, null otherwise
     */
    Sensor findSensorByUuid(UUID sensorUuid);

    /**
     * Collects and stores a new measurement for a sensor identified by its UUID.
     *
     * @param uuid the UUID of the sensor
     * @param sensorMeasurement the measurement data to be collected
     */
    void collectMeasurements(String uuid, SensorMeasurement sensorMeasurement);

    /**
     * Retrieves the current status of a sensor based on its UUID.
     *
     * @param uuid the UUID of the sensor
     * @return the current sensor status
     */
    SensorStatus getSensorStatusByUuid(String uuid);

    /**
     * Lists all alerts triggered by a sensor.
     *
     * @param uuid the UUID of the sensor
     * @return a list of alert responses for the sensor
     */
    List<AlertResponse> listSensorAlerts(String uuid);

    /**
     * Retrieves the CO2 statistics for a sensor over the last 30 days.
     *
     * @param uuid the UUID of the sensor
     * @return the CO2 statistics including maximum and average levels
     */
    CO2Statistics getSensorMetrics(String uuid);
}
