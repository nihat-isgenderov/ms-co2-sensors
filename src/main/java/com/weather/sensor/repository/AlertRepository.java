package com.weather.sensor.repository;

import com.weather.sensor.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    Optional<Alert> findTopBySensorUuidOrderByIdDesc(UUID sensorUuid);
    List<Alert> findAllBySensorId(Long id);
}
