package com.haddad.springaiintegration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ExampleIntegrationTest demonstrates how to use the {@link TestContainerBase} for integration testing.
 *
 * WHAT WAS DONE:
 * - Implemented a sample integration test that verifies the database container is running.
 *
 * HOW IT WORKS:
 * - It extends {@link TestContainerBase} to inherit the PostgreSQL container setup.
 * - It uses {@code @SpringBootTest} to bootstrap the full application context.
 * - The {@code contextLoads} test simply checks if the container successfully started and is reachable.
 *
 * WHY IT WAS IMPLEMENTED:
 * - To serve as a reference and template for future integration tests that require a real database.
 * - To confirm that the Testcontainers configuration is working correctly in the current environment.
 *
 * ARCHITECTURAL DECISIONS:
 * - Application Class Specification: The {@code classes} parameter in {@code @SpringBootTest} 
 *   is explicitly set to handle potential package mismatches between the test and the main application.
 * - Random Port: {@code webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT} is used 
 *   to avoid port conflicts during concurrent test execution.
 */
@SpringBootTest(classes = SpringAiIntegrationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ExampleIntegrationTest extends TestContainerBase {

    /**
     * Basic check to ensure that the Spring context loads correctly and the PostgreSQL container is active.
     */
    @Test
    void contextLoads() {
        assertTrue(postgres.isRunning());
    }
}
