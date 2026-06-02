package com.haddad.springaiintegration.controller;

import com.haddad.springaiintegration.dto.HealthResponse;
import com.haddad.springaiintegration.service.HealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HealthController provides a dedicated endpoint for monitoring the application's status.
 *
 * WHAT WAS DONE:
 * - Implemented a GET /api/health endpoint.
 *
 * HOW IT WORKS:
 * - It delegates the health status aggregation to {@link HealthService}.
 * - If any critical component (DB, Redis) is reported as "DOWN", it returns an HTTP 503 (Service Unavailable) status.
 * - Otherwise, it returns HTTP 200 (OK).
 *
 * WHY IT WAS IMPLEMENTED:
 * - To allow external monitoring tools (e.g., Kubernetes, Prometheus, Load Balancers) to verify
 *   the availability of the application and its critical dependencies.
 *
 * ARCHITECTURAL DECISIONS:
 * - Decoupling: We use a custom endpoint instead of exposing the default Actuator /actuator/health directly
 *   to provide a specific JSON format required by operations and to hide internal details.
 * - Status Mapping: Returning 503 on failure is a standard practice that helps automated systems
 *   quickly identify unhealthy instances without parsing the JSON body.
 */
@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthController {

    private final HealthService healthService;

    /**
     * Retrieves the current health status of the application and its dependencies.
     * @return A ResponseEntity containing the HealthResponse and appropriate HTTP status code.
     */
    @GetMapping
    public ResponseEntity<HealthResponse> getHealth() {
        HealthResponse response = healthService.checkHealth();
        if ("DOWN".equals(response.getStatus())) {
            return ResponseEntity.status(503).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
