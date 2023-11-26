package com.mjucow.eatda.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.Duration
import java.time.temporal.ChronoUnit

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOriginPatterns("*")
            .allowedMethods(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.HEAD.name(),
                HttpMethod.OPTIONS.name()
            )
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(Duration.of(60, ChronoUnit.MINUTES).toSeconds())
    }
}
