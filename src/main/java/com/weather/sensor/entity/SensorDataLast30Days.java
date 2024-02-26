package com.weather.sensor.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "mv_sensor_data_last_30_days")
public class SensorDataLast30Days implements Serializable {

    @Id
    @Column(name = "sensor_id")
    private Long sensorId;

    @Column(name = "max_last30Days")
    private Long maxLast30Days;

    @Column(name = "avg_last30Days")
    private BigDecimal avgLast30Days;

    // Constructors
    public SensorDataLast30Days() {
    }

    // Getters and Setters
    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Long getMaxLast30Days() {
        return maxLast30Days;
    }

    public void setMaxLast30Days(Long maxLast30Days) {
        this.maxLast30Days = maxLast30Days;
    }

    public BigDecimal getAvgLast30Days() {
        return avgLast30Days;
    }

    public void setAvgLast30Days(BigDecimal avgLast30Days) {
        this.avgLast30Days = avgLast30Days;
    }

    // toString, hashCode, equals methods
}
