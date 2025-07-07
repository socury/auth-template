package authtemplate.application.user

import org.springframework.stereotype.Component
import authtemplate.application.support.data.Response
import authtemplate.application.user.data.request.UpdateUserRequest
import authtemplate.domain.user.repository.UserRepository
import authtemplate.infrastructure.security.token.support.UserAuthenticationHolder

@Component
class UserUseCase(
    private val repository: UserRepository
) {
    fun update(request: UpdateUserRequest): Response {
        val user = UserAuthenticationHolder.current()
        user.update(request)
        repository.save(user)
        return Response.ok("update successful")
    }
}