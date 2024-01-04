package com.mjucow.eatda.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun swaggerApi() = OpenAPI()
        .components(Components())
        .info(
            Info()
                .title("Eatda API Documentation")
                .description("Eatda(잇다) 서비스의 API 명세서입니다.")
                .version("0.1.7")
        )
}
