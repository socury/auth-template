package authtemplate.application.support.exception

import authtemplate.core.exception.StatusCode

enum class GlobalStatusCode(
    override val status: Int,
    override val message: String
): StatusCode {
    INTERNAL_SERVER_ERROR(500, "Pang Internal Server Error"),
}