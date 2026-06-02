package com.haddad.springaiintegration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * HealthControllerIT is an integration test suite for the HealthController.
 *
 * WHAT WAS DONE:
 * - Created an integration test to verify the GET /api/health endpoint.
 *
 * HOW IT WORKS:
 * - It uses {@link MockMvc} to perform a simulated HTTP GET request to the endpoint.
 * - It asserts that the response status is 200 OK and that the JSON body contains the expected fields.
 * - The test runs within a full Spring application context (@SpringBootTest).
 *
 * WHY IT WAS IMPLEMENTED:
 * - To ensure that the controller, service, and DTO layers work together correctly (End-to-End).
 * - To prevent regressions in the health check API contract.
 *
 * ARCHITECTURAL DECISIONS:
 * - Integration Test: We chose a full context test because health checks rely on the 
 *   interaction between the application and its environment (like Actuator registry).
 * - ActiveProfiles("test"): Uses a dedicated test profile to avoid interfering with 
 *   production or development configurations.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HealthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Verifies that the health endpoint returns a successful response with all required fields.
     */
    @Test
    void shouldReturnHealthStatus() throws Exception {
        mockMvc.perform(get("/api/health")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("OK")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.components.database", notNullValue()))
                .andExpect(jsonPath("$.components.redis", notNullValue()));
    }
}
