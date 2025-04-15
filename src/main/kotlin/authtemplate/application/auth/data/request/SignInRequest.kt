package authtemplate.application.auth.data.request

data class SignInRequest(
    val username: String,
    val password: String
)