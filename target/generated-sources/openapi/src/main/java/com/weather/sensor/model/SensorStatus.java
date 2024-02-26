package com.weather.sensor.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
 * SensorStatus
 */
@lombok.Generated

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-26T22:54:49.034886+04:00[Asia/Baku]")
public class SensorStatus implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The operational status of the sensor, which can be one of the following values: - `OK`: The sensor is functioning optimally without any issues. - `WARN`: The sensor is operational but may be experiencing minor issues that should be monitored. - `ALERT`: The sensor has encountered critical issues requiring immediate attention. 
   */
  public enum StatusEnum {
    OK("OK"),
    
    WARN("WARN"),
    
    ALERT("ALERT");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private StatusEnum status;

  public SensorStatus status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * The operational status of the sensor, which can be one of the following values: - `OK`: The sensor is functioning optimally without any issues. - `WARN`: The sensor is operational but may be experiencing minor issues that should be monitored. - `ALERT`: The sensor has encountered critical issues requiring immediate attention. 
   * @return status
  */
  
  @ApiModelProperty(value = "The operational status of the sensor, which can be one of the following values: - `OK`: The sensor is functioning optimally without any issues. - `WARN`: The sensor is operational but may be experiencing minor issues that should be monitored. - `ALERT`: The sensor has encountered critical issues requiring immediate attention. ")
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SensorStatus sensorStatus = (SensorStatus) o;
    return Objects.equals(this.status, sensorStatus.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SensorStatus {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

