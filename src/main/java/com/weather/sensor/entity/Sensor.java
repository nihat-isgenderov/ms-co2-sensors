package com.weather.sensor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_sensor")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_seq")
    @SequenceGenerator(name = "sensor_seq", sequenceName = "seq_t_sensor_sequence", allocationSize = 50)
    private Long id;

    @Column(name = "sensor_uuid", nullable = false, unique = true)
    private UUID sensorUuid;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, length = 64)
    private String createdBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "modified_by", length = 64)
    private String modifiedBy;

}
