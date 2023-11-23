package com.mjucow.eatda.domain

import com.mjucow.eatda.AbstractSpringContextTest
import com.mjucow.eatda.common.config.RedisConfiguration
import com.mjucow.eatda.common.properties.RedisProperties
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Import(value = [RedisProperties::class, RedisConfiguration::class])
@DataRedisTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
abstract class AbstractCacheTest : AbstractSpringContextTest() {
    companion object {
        private const val REDIS_PORT = 6379

        @Container
        @ServiceConnection(name = "redis")
        val REDIS_CONTAINER = GenericContainer(DockerImageName.parse("redis").withTag("7.0-alpine"))
            .withExposedPorts(REDIS_PORT)
            .also {
                it.start()
                System.setProperty("spring.data.redis.host", it.host)
                System.setProperty("spring.data.redis.port", it.getMappedPort(REDIS_PORT).toString())
            }
    }
}
