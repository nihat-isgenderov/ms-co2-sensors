package com.weather.sensor.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;


import java.util.*;
import javax.annotation.Generated;

/**
 * A data point recorded by the sensor, part of the alert&#39;s measurements. It includes specific environmental readings, such as CO2 levels, that are critical for assessing conditions leading to the alert. Each measurement is uniquely identified by a UUID, facilitating precise tracking and referencing of individual data points. 
 */
@lombok.Generated

@ApiModel(description = "A data point recorded by the sensor, part of the alert's measurements. It includes specific environmental readings, such as CO2 levels, that are critical for assessing conditions leading to the alert. Each measurement is uniquely identified by a UUID, facilitating precise tracking and referencing of individual data points. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-26T22:54:49.034886+04:00[Asia/Baku]")
public class MeasurementResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private UUID id;

  private Long co2;

  public MeasurementResponse id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * The Universally Unique Identifier (UUID) for the measurement, providing a unique reference to each data point.
   * @return id
  */
  @Valid 
  @ApiModelProperty(value = "The Universally Unique Identifier (UUID) for the measurement, providing a unique reference to each data point.")
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public MeasurementResponse co2(Long co2) {
    this.co2 = co2;
    return this;
  }

  /**
   * The concentration of CO2 measured, expressed in parts per million (ppm).
   * @return co2
  */
  
  @ApiModelProperty(value = "The concentration of CO2 measured, expressed in parts per million (ppm).")
  @JsonProperty("co2")
  public Long getCo2() {
    return co2;
  }

  public void setCo2(Long co2) {
    this.co2 = co2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MeasurementResponse measurementResponse = (MeasurementResponse) o;
    return Objects.equals(this.id, measurementResponse.id) &&
        Objects.equals(this.co2, measurementResponse.co2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, co2);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MeasurementResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    co2: ").append(toIndentedString(co2)).append("\n");
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

