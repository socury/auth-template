package authtemplate.application.user

import org.springframework.stereotype.Component
import authtemplate.application.support.data.Response
import authtemplate.application.user.data.request.UpdateUserRequest
import authtemplate.infrastructure.domain.rds.user.repository.UserRepository

@Component
class UserUseCase(
    private val repository: UserRepository
) {
    fun update(request: UpdateUserRequest): Response {
        TODO("업데이트")
    }
}