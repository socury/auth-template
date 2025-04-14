package authtemplate.application.user.data.request

import java.time.LocalDate

data class UpdateUserRequest(
    val nickname: String? = null,
    val age: Int? = null,
    val postPassword: String? = null,
    val password: String? = null,
    val birthday: LocalDate? = null
)
