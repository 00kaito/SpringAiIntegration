package com.company.knowledgeassist;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * TestContainerBase provides a standardized environment for integration tests using Testcontainers.
 *
 * WHAT WAS DONE:
 * - Implemented a base class that starts a PostgreSQL container (with pgvector support) for testing.
 *
 * HOW IT WORKS:
 * - It uses the {@code @Testcontainers} and {@code @Container} annotations to manage the lifecycle 
 *   of the Docker container.
 * - The {@code configureProperties} method uses {@code @DynamicPropertySource} to override 
 *   Spring's datasource properties with the random port and credentials provided by the container.
 *
 * WHY IT WAS IMPLEMENTED:
 * - To ensure that integration tests run against a real database instance that matches the production 
 *   environment (PostgreSQL 17 with pgvector), avoiding issues caused by H2 or other in-memory databases.
 *
 * ARCHITECTURAL DECISIONS:
 * - Singleton Container Pattern: By using a {@code static} container in a base class, we can potentially 
 *   reuse the same container across multiple test classes, significantly speeding up the test suite.
 * - Specific Image: Using "pgvector/pgvector:pg17" ensures that vector-based features of the application 
 *   can be tested accurately.
 */
@Testcontainers
public abstract class TestContainerBase {

    /**
     * The PostgreSQL container instance.
     */
    @Container
    protected static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("pgvector/pgvector:pg17")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    /**
     * Dynamically registers database properties provided by the Testcontainer.
     * @param registry The DynamicPropertyRegistry to which properties are added.
     */
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
