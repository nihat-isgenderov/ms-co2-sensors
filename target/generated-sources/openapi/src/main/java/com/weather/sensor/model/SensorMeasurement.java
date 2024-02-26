package com.weather.sensor.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;


import java.util.*;
import javax.annotation.Generated;

/**
 * A schema defining the structure of a sensor measurement submission. It includes both the CO2 concentration and the exact time the measurement was recorded.
 */
@lombok.Generated

@ApiModel(description = "A schema defining the structure of a sensor measurement submission. It includes both the CO2 concentration and the exact time the measurement was recorded.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-26T22:54:49.034886+04:00[Asia/Baku]")
public class SensorMeasurement implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long co2;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime time;

  public SensorMeasurement co2(Long co2) {
    this.co2 = co2;
    return this;
  }

  /**
   * CO2 concentration measured by the sensor, specified in parts per million (ppm). This measurement is vital for tracking air quality and identifying potential environmental concerns.
   * @return co2
  */
  
  @ApiModelProperty(value = "CO2 concentration measured by the sensor, specified in parts per million (ppm). This measurement is vital for tracking air quality and identifying potential environmental concerns.")
  @JsonProperty("co2")
  public Long getCo2() {
    return co2;
  }

  public void setCo2(Long co2) {
    this.co2 = co2;
  }

  public SensorMeasurement time(OffsetDateTime time) {
    this.time = time;
    return this;
  }

  /**
   * The timestamp marking when the measurement was taken, according to ISO 8601 format. This precision ensures that each data point can be accurately placed within a temporal context for analysis.
   * @return time
  */
  @Valid 
  @ApiModelProperty(value = "The timestamp marking when the measurement was taken, according to ISO 8601 format. This precision ensures that each data point can be accurately placed within a temporal context for analysis.")
  @JsonProperty("time")
  public OffsetDateTime getTime() {
    return time;
  }

  public void setTime(OffsetDateTime time) {
    this.time = time;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SensorMeasurement sensorMeasurement = (SensorMeasurement) o;
    return Objects.equals(this.co2, sensorMeasurement.co2) &&
        Objects.equals(this.time, sensorMeasurement.time);
  }

  @Override
  public int hashCode() {
    return Objects.hash(co2, time);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SensorMeasurement {\n");
    sb.append("    co2: ").append(toIndentedString(co2)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

