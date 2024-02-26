package com.weather.sensor.repository;

import com.weather.sensor.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    
    Sensor findBySensorUuid(UUID sensorUuid);
}
