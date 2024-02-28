package com.weather.sensor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.sensor.model.AlertResponse;
import com.weather.sensor.model.CO2Statistics;
import com.weather.sensor.model.SensorMeasurement;
import com.weather.sensor.model.SensorStatus;
import com.weather.sensor.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SensorController.class)
class SensorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorService sensorService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String sensorUuid = UUID.randomUUID().toString();
    private SensorMeasurement sensorMeasurement;
    private SensorStatus sensorStatus;
    private CO2Statistics co2Statistics;
    private List<AlertResponse> alertResponses;

    @BeforeEach
    void setUp() {
        sensorMeasurement = new SensorMeasurement(); // Initialize with test data
        sensorMeasurement.setCo2(200L);
        sensorMeasurement.setTime(LocalDateTime.now().atOffset(ZoneOffset.UTC));
        sensorStatus = new SensorStatus(); // Initialize with test data
        sensorStatus.setStatus(SensorStatus.StatusEnum.OK);
        co2Statistics = new CO2Statistics(); // Initialize with test data
        alertResponses = Collections.singletonList(new AlertResponse()); // Initialize with test data
    }

    @Test
    void collectMeasurements_ShouldReturnOkStatus() throws Exception {
        doNothing().when(sensorService).collectMeasurements(any(), any());
        mockMvc.perform(post("/api/v1/sensors/{uuid}/measurements/", sensorUuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sensorMeasurement)))
                .andExpect(status().isOk());
    }

    @Test
    void getSensorStatus_ShouldReturnSensorStatus() throws Exception {
        given(sensorService.getSensorStatusByUuid(sensorUuid)).willReturn(sensorStatus);

        mockMvc.perform(get("/api/v1/sensors/{uuid}", sensorUuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sensorStatus)));
    }

    @Test
    void listSensorAlerts_ShouldReturnAlertsList() throws Exception {
        given(sensorService.listSensorAlerts(sensorUuid)).willReturn(alertResponses);

        mockMvc.perform(get("/api/v1/sensors/{uuid}/alerts", sensorUuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(alertResponses)));
    }

    @Test
    void getSensorMetrics_ShouldReturnCO2Statistics() throws Exception {
        given(sensorService.getSensorMetrics(sensorUuid)).willReturn(co2Statistics);

        mockMvc.perform(get("/api/v1/sensors/{uuid}/metrics", sensorUuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(co2Statistics)));
    }
}
