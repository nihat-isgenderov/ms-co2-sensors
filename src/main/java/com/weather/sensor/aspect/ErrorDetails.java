package com.weather.sensor.aspect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    private int statusCode;
    private String message;
    private String details;
}
