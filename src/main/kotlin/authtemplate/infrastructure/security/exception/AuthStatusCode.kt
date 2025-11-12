package authtemplate.infrastructure.security.exception

import authtemplate.infrastructure.common.exception.StatusCode

enum class AuthStatusCode(
    override val status: Int,
    override val message: String
): StatusCode {
    REFRESH_TOKEN_NOT_MATCH(404, " not found"),
}