package authtemplate.presentation.user

import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import authtemplate.application.user.UserUseCase
import authtemplate.application.user.data.request.UpdateUserRequest
import authtemplate.application.support.data.Response

@RestController
@RequestMapping("/user")
class UserController(
    private val useCase: UserUseCase
) {
//    @PatchMapping
//    fun update(@RequestBody request: UpdateUserRequest): Response {
//        return useCase.update(request);
//    }
}