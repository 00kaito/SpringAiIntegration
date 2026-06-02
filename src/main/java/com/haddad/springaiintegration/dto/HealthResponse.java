package com.haddad.springaiintegration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * HealthResponse is a Data Transfer Object (DTO) that defines the contract for the health check API.
 *
 * WHAT WAS DONE:
 * - Created a structured DTO to represent the application's health status.
 *
 * HOW IT WORKS:
 * - It holds the overall status, a timestamp of the check, and a map of individual component statuses.
 * - Uses Lombok annotations (@Data, @Builder) to reduce boilerplate code.
 *
 * WHY IT WAS IMPLEMENTED:
 * - To provide a consistent and predictable JSON structure for monitoring tools.
 *
 * ARCHITECTURAL DECISIONS:
 * - Specificity: By creating a custom DTO rather than returning a Map or Actuator's internal classes, 
 *   we ensure that the API contract is explicit and stable.
 * - Components Map: Using a Map for components allows for easy extension if more dependencies 
 *   (e.g., messaging queues, external APIs) are added to the health check in the future.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthResponse {
    private String status;
    private Instant timestamp;
    private Map<String, String> components;
}
