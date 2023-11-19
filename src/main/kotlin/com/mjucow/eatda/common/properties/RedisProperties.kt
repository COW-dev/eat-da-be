package com.mjucow.eatda.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data.redis")
data class RedisProperties(
    var host: String = "",
    var port: Int = -1,
)
