package com.weather.sensor.repository;

import com.weather.sensor.entity.SensorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SensorStatusRepository extends JpaRepository<SensorStatus, Long> {

    SensorStatus findBySensorId(Long id);
}
