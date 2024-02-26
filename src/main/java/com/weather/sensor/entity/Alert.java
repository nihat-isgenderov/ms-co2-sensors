package com.weather.sensor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_alert")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Alert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alert_seq")
    @SequenceGenerator(name = "alert_seq", sequenceName = "seq_t_alert_sequence", allocationSize = 50)
    private Long id;

    @Column(name = "sensor_uuid", nullable = false)
    private UUID sensorUuid;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at", nullable = false)
    private LocalDateTime endedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;
}
