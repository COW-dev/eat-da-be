package com.mjucow.eatda.domain

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
abstract class AbstractDataTest {
    companion object {
        @Container
        @ServiceConnection
        val POSTGRESQL_CONTAINER = PostgreSQLContainer("postgres:16.0-alpine")
            .withUsername("test-user")
            .withPassword("test-password")
            .withDatabaseName("test_db")!!
    }
}