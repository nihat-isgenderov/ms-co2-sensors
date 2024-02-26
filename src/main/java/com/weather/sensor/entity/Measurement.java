package com.weather.sensor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_measurement")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Measurement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "measurement_seq")
    @SequenceGenerator(name = "measurement_seq", sequenceName = "seq_t_measurement_sequence", allocationSize = 50)
    private Long id;

    @Column(name = "sensor_uuid", nullable = false)
    private UUID sensorUuid;

    @Column(name = "co2_amount", nullable = false)
    private Long co2Amount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;
}
