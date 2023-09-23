package com.mjucow.eatda

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class EatdaApplication

fun main(args: Array<String>) {
    runApplication<EatdaApplication>(*args)
}
