package authtemplate.application.auth.data.response

data class TokenResponse(
    val access: String,
    val refresh: String,
)
