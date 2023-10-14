package com.mjucow.eatda.common.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfiguration {
    /**
     * 기본 objectMapper 는 java8 이후 시간관련 타입(Instant 등)을 변환하지 못하므로 JavaTimeModule 추가 등록
     */
    @Bean
    fun objectMapper(): ObjectMapper {
        return jsonMapper {
            addModule(kotlinModule())
            addModule(JavaTimeModule())
            configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
        }
    }
}
