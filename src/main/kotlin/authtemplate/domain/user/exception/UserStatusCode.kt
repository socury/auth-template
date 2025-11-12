package authtemplate.domain.user.exception

import authtemplate.infrastructure.common.exception.StatusCode

enum class UserStatusCode(
    override val status: Int,
    override val message: String
): StatusCode {
    USER_NOT_FOUND(404, "User not found"),
    USER_ALREADY_EXISTS(409, "User already exists"),
    PASSWORD_NOT_MATCH(400, "Password doesn't match"),
}