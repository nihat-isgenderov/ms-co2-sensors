package com.weather.sensor.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.weather.sensor.model.MeasurementResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;


import java.util.*;
import javax.annotation.Generated;

/**
 * Represents a significant event detected by the sensor, characterized by specific conditions that exceed predefined thresholds. The alert encompasses a start and end time, delineating the duration of the event. It also includes a series of measurements taken during this period, each of which contributes to the rationale behind the alert&#39;s initiation. 
 */
@lombok.Generated

@ApiModel(description = "Represents a significant event detected by the sensor, characterized by specific conditions that exceed predefined thresholds. The alert encompasses a start and end time, delineating the duration of the event. It also includes a series of measurements taken during this period, each of which contributes to the rationale behind the alert's initiation. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-26T22:54:49.034886+04:00[Asia/Baku]")
public class AlertResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime startTime;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime endTime;

  @Valid
  private List<@Valid MeasurementResponse> measurements;

  public AlertResponse startTime(OffsetDateTime startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * The ISO 8601 formatted date and time marking the beginning of the alert period.
   * @return startTime
  */
  @Valid 
  @ApiModelProperty(value = "The ISO 8601 formatted date and time marking the beginning of the alert period.")
  @JsonProperty("startTime")
  public OffsetDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(OffsetDateTime startTime) {
    this.startTime = startTime;
  }

  public AlertResponse endTime(OffsetDateTime endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * The ISO 8601 formatted date and time marking the conclusion of the alert period.
   * @return endTime
  */
  @Valid 
  @ApiModelProperty(value = "The ISO 8601 formatted date and time marking the conclusion of the alert period.")
  @JsonProperty("endTime")
  public OffsetDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(OffsetDateTime endTime) {
    this.endTime = endTime;
  }

  public AlertResponse measurements(List<@Valid MeasurementResponse> measurements) {
    this.measurements = measurements;
    return this;
  }

  public AlertResponse addMeasurementsItem(MeasurementResponse measurementsItem) {
    if (this.measurements == null) {
      this.measurements = new ArrayList<>();
    }
    this.measurements.add(measurementsItem);
    return this;
  }

  /**
   * A collection of measurements recorded during the alert period, detailing the data points that led to the alert being triggered.
   * @return measurements
  */
  @Valid 
  @ApiModelProperty(value = "A collection of measurements recorded during the alert period, detailing the data points that led to the alert being triggered.")
  @JsonProperty("measurements")
  public List<@Valid MeasurementResponse> getMeasurements() {
    return measurements;
  }

  public void setMeasurements(List<@Valid MeasurementResponse> measurements) {
    this.measurements = measurements;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlertResponse alertResponse = (AlertResponse) o;
    return Objects.equals(this.startTime, alertResponse.startTime) &&
        Objects.equals(this.endTime, alertResponse.endTime) &&
        Objects.equals(this.measurements, alertResponse.measurements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startTime, endTime, measurements);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlertResponse {\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    measurements: ").append(toIndentedString(measurements)).append("\n");
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

