package com.weather.sensor.enums;

public enum SensorStatusEnum {
    OK("OK"),

    WARN("WARN"),

    ALERT("ALERT");

    private String value;
    SensorStatusEnum(String value) {
        this.value = value;
    }


    public static SensorStatusEnum fromValue(String value) {
        for (SensorStatusEnum b : SensorStatusEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
