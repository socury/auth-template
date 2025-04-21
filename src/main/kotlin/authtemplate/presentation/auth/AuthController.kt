package authtemplate.presentation.auth

import authtemplate.application.auth.AuthUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import authtemplate.application.auth.data.request.RefreshRequest
import authtemplate.application.auth.data.request.SignInRequest
import authtemplate.application.auth.data.request.SignUpRequest
import authtemplate.application.auth.data.response.TokenResponse
import authtemplate.application.support.data.DataResponse
import authtemplate.application.support.data.Response
import org.springframework.web.bind.annotation.*

@Tag(name = "Auth", description = "인증/인가")
@RestController
@RequestMapping("/auth")
class AuthController(
    private val useCase: AuthUseCase
) {
    @Operation(summary = "Sign Up")
    @PostMapping("/sign-up")
    fun signUp(@RequestBody request: SignUpRequest): Response {
        return useCase.register(request);
    }

    @Operation(summary = "Login")
    @PostMapping("/sign-in")
    fun signIn(@RequestBody request: SignInRequest): DataResponse<TokenResponse> {
        return useCase.login(request)
    }

    @Operation(summary = "Token Refresh")
    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshRequest): DataResponse<TokenResponse> {
        return useCase.refresh(request)
    }
}