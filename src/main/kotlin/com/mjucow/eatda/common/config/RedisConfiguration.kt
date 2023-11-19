package com.mjucow.eatda.common.config

import com.mjucow.eatda.common.properties.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfiguration(
    private val redisProperties: RedisProperties
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(
            RedisStandaloneConfiguration(redisProperties.host, redisProperties.port)
        )
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            connectionFactory = redisConnectionFactory
            setDefaultSerializer(StringRedisSerializer())
        }
    }
}
