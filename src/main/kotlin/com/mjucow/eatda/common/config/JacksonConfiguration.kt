package com.mjucow.eatda.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfiguration {
    /**
     * 기본 objectMapper 는 java8 이후 시간관련 타입(Instant 등)을 변환하지 못하므로 JavaTimeModule 추가 등록
     */
    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper().registerModule(JavaTimeModule())
    }
}
