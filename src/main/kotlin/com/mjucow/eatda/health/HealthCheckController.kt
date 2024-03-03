package com.mjucow.eatda.health

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health-check")
class HealthCheckController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun healthCheck(): String {
        return "ok"
    }
}
