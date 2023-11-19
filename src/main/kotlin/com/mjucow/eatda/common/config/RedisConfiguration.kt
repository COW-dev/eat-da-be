package com.mjucow.eatda.common.config

import com.mjucow.eatda.common.properties.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate

@Configuration
class RedisConfiguration(
    private val redisProperties: RedisProperties
) {
    init {
        println(redisProperties)
    }
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        println("redisConnectionFactory")
        return LettuceConnectionFactory(
            RedisStandaloneConfiguration(redisProperties.host, redisProperties.port)
        )
    }

    @Bean
    fun stringRedisTemplate(redisConnectionFactory: RedisConnectionFactory): StringRedisTemplate {
        println("StringRedisTemplate")
        return StringRedisTemplate().apply {
            connectionFactory = redisConnectionFactory
        }
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        println("redisTemplate")
        return RedisTemplate<String, Any>().apply {
            connectionFactory = redisConnectionFactory
        }
    }
}
