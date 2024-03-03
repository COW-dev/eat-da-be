package com.mjucow.eatda.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig(
    @Value("\${swagger.api-url}")
    val apiUrl: String,
) {
    @Bean
    fun swaggerApi(): OpenAPI = OpenAPI()
        .components(Components())
        .servers(listOf(Server().apply { url = apiUrl }))
        .info(
            Info()
                .title("Eatda API Documentation")
                .description("Eatda(잇다) 서비스의 API 명세서입니다.")
                .version("0.1.8")
        )
}
