package com.mjucow.eatda.presentation.common

import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.RestControllerAdvice

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Order(0)
@RestControllerAdvice
annotation class DomainControllerAdvice
