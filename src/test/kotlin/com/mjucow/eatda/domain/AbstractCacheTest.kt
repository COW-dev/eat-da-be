package com.mjucow.eatda.domain

import com.mjucow.eatda.common.config.RedisConfiguration
import com.mjucow.eatda.common.properties.PropertiesConfiguration
import com.mjucow.eatda.common.properties.RedisProperties
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Import(value = [
    RedisProperties::class,
    RedisConfiguration::class,
    PropertiesConfiguration::class,
])
@DataRedisTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
abstract class AbstractCacheTest {
    companion object {
        @Container
        @ServiceConnection
        val REDIS_CONTAINER = GenericContainer("redis:7.0-alpine")
            .withExposedPorts(6379)
            .withReuse(true)
    }
}
