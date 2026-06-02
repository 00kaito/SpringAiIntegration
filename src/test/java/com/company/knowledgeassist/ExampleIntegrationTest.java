package com.company.knowledgeassist;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Example integration test using Testcontainers.
 */
@SpringBootTest(classes = com.haddad.springaiintegration.SpringAiIntegrationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ExampleIntegrationTest extends TestContainerBase {

    @Test
    void contextLoads() {
        assertTrue(postgres.isRunning());
    }
}
