package com.joseph.jblog.exception

import com.joseph.jblog.payload.ErrorDetails
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.time.LocalDate

@ControllerAdvice
class GlobalExceptionHandler {
    // Handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(
        exception: ResourceNotFoundException,
        webRequest: WebRequest
    ): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            LocalDate.now(), exception.message,
            webRequest.getDescription(true)
        )
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    // handle global exception
    @ExceptionHandler(Exception::class)
    fun handleGlobalException(
        exception: ResourceNotFoundException,
        webRequest: WebRequest
    ): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            LocalDate.now(), exception.message,
            webRequest.getDescription(false)
        )
        return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}