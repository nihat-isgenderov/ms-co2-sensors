package com.weather.sensor.entity;

import com.weather.sensor.enums.SensorStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_sensor_status")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SensorStatus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_status_seq")
    @SequenceGenerator(name = "sensor_status_seq", sequenceName = "seq_t_sensor_status_sequence", allocationSize = 50)
    private Long id;

    @Column(name = "sensor_uuid", nullable = false)
    private UUID sensorUuid;

    @Column(name = "status", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private SensorStatusEnum status;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "warn_count", nullable = false)
    private Integer warnCount;

    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

}
