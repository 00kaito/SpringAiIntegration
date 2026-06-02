package com.haddad.springaiintegration.controller;

import com.haddad.springaiintegration.dto.HealthResponse;
import com.haddad.springaiintegration.service.HealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthController {

    private final HealthService healthService;

    @GetMapping
    public ResponseEntity<HealthResponse> getHealth() {
        HealthResponse response = healthService.checkHealth();
        if ("DOWN".equals(response.getStatus())) {
            return ResponseEntity.status(503).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
