package com.joseph.jblog.exception
import com.joseph.jblog.exception.PrayerAPIException
import com.joseph.jblog.payload.ErrorDetails
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import java.util.function.Consumer

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    // handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(
        exception: ResourceNotFoundException,
        webRequest: WebRequest
    ): ResponseEntity<ErrorDetails> {
        val errorDetails = exception.message?.let {
            ErrorDetails(
                Date(), it,
                webRequest.getDescription(false)
            )
        }
        return ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(PrayerAPIException::class)
    fun handleBlogAPIException(
        exception: PrayerAPIException,
        webRequest: WebRequest
    ): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            Date(), exception.message,
            webRequest.getDescription(false)
        )
        return ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST)
    }

    // global exceptions
    @ExceptionHandler(Exception::class)
    fun handleGlobalException(
        exception: Exception,
        webRequest: WebRequest
    ): ResponseEntity<ErrorDetails> {
        val errorDetails = exception.message?.let {
            ErrorDetails(
                Date(), it,
                webRequest.getDescription(false)
            )
        }
        return ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val message = error.getDefaultMessage()
            errors[fieldName] = message
        })
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    } //    @ExceptionHandler(MethodArgumentNotValidException.class)
    //    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
    //                                                                        WebRequest webRequest){
    //        Map<String, String> errors = new HashMap<>();
    //        exception.getBindingResult().getAllErrors().forEach((error) ->{
    //            String fieldName = ((FieldError)error).getField();
    //            String message = error.getDefaultMessage();
    //            errors.put(fieldName, message);
    //        });
    //        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    //    }
}