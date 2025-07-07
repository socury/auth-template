package authtemplate.application.auth.data.request

import jakarta.validation.constraints.Email
import authtemplate.domain.user.entity.UserEntity
import authtemplate.domain.user.enumeration.UserRole

data class SignUpRequest(
    @Email val email: String,
    val username: String,
    val password: String,
) {
    fun toEntity(encodedPassword: String): UserEntity {
        return UserEntity(
            email = email,
            username = username,
            password = encodedPassword,
            role = UserRole.MEMBER
        )
    }
}