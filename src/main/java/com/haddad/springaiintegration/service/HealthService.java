package com.haddad.springaiintegration.service;

import com.haddad.springaiintegration.dto.HealthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthContributorRegistry;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HealthService {

    private final HealthContributorRegistry healthContributorRegistry;

    public HealthResponse checkHealth() {
        Map<String, String> componentStatuses = new HashMap<>();
        boolean overallUp = true;

        // Check Database
        String dbStatus = getComponentStatus("db");
        componentStatuses.put("database", dbStatus);
        if (!Status.UP.getCode().equals(dbStatus)) {
            overallUp = false;
        }

        // Check Redis
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

    private String getComponentStatus(String name) {
        HealthContributor contributor = healthContributorRegistry.getContributor(name);
        if (contributor instanceof HealthIndicator indicator) {
            return indicator.health().getStatus().getCode();
        }
        return Status.UNKNOWN.getCode();
    }
}
