package com.weather.sensor.repository;

import com.weather.sensor.entity.SensorDataLast30Days;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MetricsRepository extends JpaRepository<SensorDataLast30Days, Long> {
}
