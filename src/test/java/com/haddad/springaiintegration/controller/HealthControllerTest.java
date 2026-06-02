package com.haddad.springaiintegration.controller;

import com.haddad.springaiintegration.controller.HealthController;
import com.haddad.springaiintegration.dto.HealthResponse;
import com.haddad.springaiintegration.service.HealthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * HealthControllerTest is a focused unit test for the {@link HealthController}.
 *
 * WHAT WAS DONE:
 * - Implemented unit tests for the health check endpoint using {@code @WebMvcTest}.
 *
 * HOW IT WORKS:
 * - It uses {@code @WebMvcTest(HealthController.class)} to load only the web layer, keeping the test fast.
 * - The {@code HealthService} is mocked using {@code @MockBean}, allowing us to simulate 
 *   both healthy and unhealthy system states.
 * - {@link MockMvc} is used to perform requests and assert on the HTTP status and JSON response body.
 *
 * WHY IT WAS IMPLEMENTED:
 * - To verify the controller's logic independently of the actual health check implementation.
 * - Specifically, it ensures that the controller correctly maps service responses to 
 *   the appropriate HTTP status codes (200 OK vs 503 Service Unavailable).
 *
 * ARCHITECTURAL DECISIONS:
 * - Sliced Testing: Using {@code @WebMvcTest} instead of a full {@code @SpringBootTest} 
 *   follows the "Testing Pyramid" principle, providing fast feedback for the web layer.
 * - Mocking Service: By mocking the service, we avoid the need for real DB or Redis connections 
 *   during unit testing, making the tests deterministic and isolated.
 */
@WebMvcTest(HealthController.class)
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HealthService healthService;

    /**
     * Verifies that the endpoint returns 200 OK when the service reports the system is healthy.
     */
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

    /**
     * Verifies that the endpoint returns 503 Service Unavailable when any critical component is down.
     */
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
