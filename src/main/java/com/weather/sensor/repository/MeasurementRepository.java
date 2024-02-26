package com.weather.sensor.repository;

import com.weather.sensor.entity.Measurement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    @Query(value = "SELECT m FROM Measurement m WHERE m.sensorUuid = :sensorUuid ORDER BY m.createdAt DESC")
    List<Measurement> findBySensorUuidOrderByCreatedAtDesc(@Param("sensorUuid") UUID sensorUuid, Pageable pageable);

    @Query(value = "SELECT * FROM t_measurement WHERE sensor_id = :sensorId AND created_at BETWEEN :startDate AND :endDate ORDER BY id ASC LIMIT 3", nativeQuery = true)
    List<Measurement> findTop3BySensorIdAndDateRangeOrderedNative(
            @Param("sensorId") Long sensorId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
