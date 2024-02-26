package com.weather.sensor.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;


import java.util.*;
import javax.annotation.Generated;

/**
 * This schema encapsulates the CO2 level statistics measured by environmental sensors, specifically focusing on the last 30 days. It includes both the peak CO2 concentration recorded within this timeframe, which can indicate the highest level of environmental impact or pollution, and the average concentration, offering insights into overall air quality trends during the period. 
 */
@lombok.Generated

@ApiModel(description = "This schema encapsulates the CO2 level statistics measured by environmental sensors, specifically focusing on the last 30 days. It includes both the peak CO2 concentration recorded within this timeframe, which can indicate the highest level of environmental impact or pollution, and the average concentration, offering insights into overall air quality trends during the period. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-26T22:54:49.034886+04:00[Asia/Baku]")
public class CO2Statistics implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long maxLast30Days;

  private Double avgLast30Days;

  public CO2Statistics maxLast30Days(Long maxLast30Days) {
    this.maxLast30Days = maxLast30Days;
    return this;
  }

  /**
   * Represents the highest CO2 concentration (in parts per million, ppm) detected by the sensor over the past 30 days. This metric is crucial for identifying peak pollution events or moments of significant environmental stress. 
   * @return maxLast30Days
  */
  
  @ApiModelProperty(value = "Represents the highest CO2 concentration (in parts per million, ppm) detected by the sensor over the past 30 days. This metric is crucial for identifying peak pollution events or moments of significant environmental stress. ")
  @JsonProperty("maxLast30Days")
  public Long getMaxLast30Days() {
    return maxLast30Days;
  }

  public void setMaxLast30Days(Long maxLast30Days) {
    this.maxLast30Days = maxLast30Days;
  }

  public CO2Statistics avgLast30Days(Double avgLast30Days) {
    this.avgLast30Days = avgLast30Days;
    return this;
  }

  /**
   * Reflects the mean CO2 concentration (in ppm) observed by the sensor throughout the last 30 days. Averaging these values provides a general sense of air quality and helps in assessing long-term environmental conditions and compliance with air quality standards. 
   * @return avgLast30Days
  */
  
  @ApiModelProperty(value = "Reflects the mean CO2 concentration (in ppm) observed by the sensor throughout the last 30 days. Averaging these values provides a general sense of air quality and helps in assessing long-term environmental conditions and compliance with air quality standards. ")
  @JsonProperty("avgLast30Days")
  public Double getAvgLast30Days() {
    return avgLast30Days;
  }

  public void setAvgLast30Days(Double avgLast30Days) {
    this.avgLast30Days = avgLast30Days;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CO2Statistics co2Statistics = (CO2Statistics) o;
    return Objects.equals(this.maxLast30Days, co2Statistics.maxLast30Days) &&
        Objects.equals(this.avgLast30Days, co2Statistics.avgLast30Days);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxLast30Days, avgLast30Days);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CO2Statistics {\n");
    sb.append("    maxLast30Days: ").append(toIndentedString(maxLast30Days)).append("\n");
    sb.append("    avgLast30Days: ").append(toIndentedString(avgLast30Days)).append("\n");
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

