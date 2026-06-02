package com.haddad.springaiintegration.service;

import com.haddad.springaiintegration.dto.HealthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.health.contributor.HealthContributor;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.boot.health.contributor.Status;
import org.springframework.boot.health.registry.HealthContributorRegistry;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * HealthService is responsible for aggregating health information from various system components.
 *
 * WHAT WAS DONE:
 * - Implemented logic to query specific health indicators (Database and Redis).
 *
 * HOW IT WORKS:
 * - It uses the {@link HealthContributorRegistry} to access Spring Boot Actuator's health indicators.
 * - Specifically, it looks for "db" and "redis" contributors.
 * - It maps the status of these contributors to a custom {@link HealthResponse} DTO.
 *
 * WHY IT WAS IMPLEMENTED:
 * - To provide a centralized business logic for health status determination, separating 
 *   the aggregation logic from the controller.
 *
 * ARCHITECTURAL DECISIONS:
 * - Leverage Actuator: Instead of manual ping logic (e.g., executing "SELECT 1"), we reuse 
 *   Actuator's built-in indicators. This is more reliable and integrates with Spring's 
 *   auto-configuration for various data sources.
 * - Aggregation: The service determines an overall "OK" status only if all critical components are "UP".
 */
@Service
@RequiredArgsConstructor
public class HealthService {

    private final HealthContributorRegistry healthContributorRegistry;

    /**
     * Performs a health check on critical dependencies and aggregates the results.
     * @return A HealthResponse containing the overall status and individual component details.
     */
    public HealthResponse checkHealth() {
        Map<String, String> componentStatuses = new HashMap<>();
        boolean overallUp = true;

        // Check Database - "db" is the default name for DataSourceHealthIndicator
        String dbStatus = getComponentStatus("db");
        componentStatuses.put("database", dbStatus);
        if (!Status.UP.getCode().equals(dbStatus)) {
            overallUp = false;
        }

        // Check Redis - "redis" is the default name for RedisHealthIndicator
        String redisStatus = getComponentStatus("redis");
        componentStatuses.put("redis", redisStatus);
        if (!Status.UP.getCode().equals(redisStatus)) {
            overallUp = false;
        }

        return HealthResponse.builder()
                .status(overallUp ? "OK" : Status.DOWN.getCode())
                .timestamp(Instant.now())
                .components(componentStatuses)
                .build();
    }

    /**
     * Safely retrieves the status code for a given health contributor name.
     * @param name The name of the health contributor (e.g., "db", "redis").
     * @return The status code string (e.g., "UP", "DOWN", "UNKNOWN").
     */
    private String getComponentStatus(String name) {
        HealthContributor contributor = healthContributorRegistry.getContributor(name);
        if (contributor instanceof HealthIndicator indicator) {
            return indicator.health().getStatus().getCode();
        }
        return Status.UNKNOWN.getCode();
    }
}
