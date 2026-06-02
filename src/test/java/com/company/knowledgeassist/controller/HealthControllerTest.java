package com.company.knowledgeassist.controller;

import com.haddad.springaiintegration.controller.HealthController;
import com.haddad.springaiintegration.dto.HealthResponse;
import com.haddad.springaiintegration.service.HealthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthController.class)
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HealthService healthService;

    @Test
    void getHealth_shouldReturnOk_whenStatusIsOk() throws Exception {
        HealthResponse response = HealthResponse.builder()
                .status("OK")
                .timestamp(Instant.now())
                .components(Map.of("database", "UP", "redis", "UP"))
                .build();

        when(healthService.checkHealth()).thenReturn(response);

        mockMvc.perform(get("/api/health")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.components.database").value("UP"))
                .andExpect(jsonPath("$.components.redis").value("UP"));
    }

    @Test
    void getHealth_shouldReturnServiceUnavailable_whenStatusIsDown() throws Exception {
        HealthResponse response = HealthResponse.builder()
                .status("DOWN")
                .timestamp(Instant.now())
                .components(Map.of("database", "DOWN", "redis", "UP"))
                .build();

        when(healthService.checkHealth()).thenReturn(response);

        mockMvc.perform(get("/api/health")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value("DOWN"));
    }
}
