package authtemplate.application.support.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import authtemplate.application.support.data.ErrorResponse
import authtemplate.infrastructure.common.exception.BasicException

@RestControllerAdvice
class BasicExceptionHandler {
    @ExceptionHandler(BasicException::class)
    fun basicExceptionHandler(e: BasicException): ResponseEntity<ErrorResponse> {
        return ErrorResponse.responseEntity(e.statusCode);
    }
    @ExceptionHandler(Exception::class)
    fun handleAll(e: Exception): ResponseEntity<ErrorResponse> {
        return ErrorResponse.responseEntity(GlobalStatusCode.INTERNAL_SERVER_ERROR)
    }
}