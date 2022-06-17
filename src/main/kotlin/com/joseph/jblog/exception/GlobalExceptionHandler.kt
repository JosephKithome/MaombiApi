package com.joseph.jblog.exception

import com.joseph.jblog.payload.ErrorDetails
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDate

@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {
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

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors: MutableMap<String,String> = mutableMapOf()

        ex.bindingResult.allErrors.forEach { error->
            val fieldName = error.objectName as FieldError
            val message = error.defaultMessage

            errors[fieldName.toString()] = message!!

        }

        return ResponseEntity(errors,HttpStatus.BAD_REQUEST)
    }
}