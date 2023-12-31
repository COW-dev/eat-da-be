package com.mjucow.eatda.presentation.common

import com.mjucow.eatda.common.logger
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@GlobalControllerAdvice
class GlobalExceptionHandler {

    val log = logger()

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(exception: IllegalArgumentException): ApiResponse<Unit> {
        log.warn(exception.message)
        return ApiResponse.error(exception.message)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(exception: EntityNotFoundException): ApiResponse<Unit> {
        log.warn(exception.message)
        return ApiResponse.error(exception.message)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exception: RuntimeException): ApiResponse<Unit> {
        log.error(exception.stackTraceToString())
        return ApiResponse.error(exception.message)
    }
}
