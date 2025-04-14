package authtemplate.application.support.data

import org.springframework.http.ResponseEntity
import authtemplate.core.exception.StatusCode

class ErrorResponse(
    override val status: Int,
    override val message: String,
): Response(status, message) {
    companion object {
        fun responseEntity(statusCode: StatusCode): ResponseEntity<ErrorResponse> {
            return ResponseEntity.status(statusCode.status).body(
                ErrorResponse(
                    statusCode.status,
                    statusCode.message
                )
            )
        }
    }
}