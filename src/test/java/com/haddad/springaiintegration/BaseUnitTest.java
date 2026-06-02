package com.haddad.springaiintegration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * BaseUnitTest serves as the foundational class for all Mockito-based unit tests.
 *
 * WHAT WAS DONE:
 * - Created a base class configured with MockitoExtension.
 *
 * HOW IT WORKS:
 * - By extending this class, subclasses automatically inherit the {@link MockitoExtension}.
 * - This enables the use of {@code @Mock}, {@code @Spy}, and {@code @InjectMocks} annotations
 *   without manually initializing mocks in each test class.
 *
 * WHY IT WAS IMPLEMENTED:
 * - To reduce boilerplate code in unit tests and ensure a consistent testing setup across the project.
 *
 * ARCHITECTURAL DECISIONS:
 * - Standardized Extension: Using a base class with {@code @ExtendWith(MockitoExtension.class)} 
 *   ensures that all unit tests follow the same lifecycle and mock initialization strategy.
 */
@ExtendWith(MockitoExtension.class)
public abstract class BaseUnitTest {
}
