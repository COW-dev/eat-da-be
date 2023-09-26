package com.mjucow.eatda

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
@ActiveProfiles("test")
abstract class AbstractApplicationTest {
    companion object {
        @Container
        @ServiceConnection
        val POSTGRESQL_CONTAINER = PostgreSQLContainer("postgres:16.0-alpine")
            .withUsername("test-user")
            .withPassword("test-password")
            .withDatabaseName("test_db")!!
    }
}
