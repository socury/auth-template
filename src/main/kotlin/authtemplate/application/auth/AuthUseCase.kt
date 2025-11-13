package authtemplate.application.auth

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import authtemplate.application.auth.data.request.RefreshRequest
import authtemplate.application.auth.data.request.SignInRequest
import authtemplate.application.auth.data.request.SignUpRequest
import authtemplate.application.auth.data.response.TokenResponse
import authtemplate.application.support.data.DataResponse
import authtemplate.application.support.data.Response
import authtemplate.domain.user.entity.UserEntity
import authtemplate.domain.user.exception.PasswordNotMatchException
import authtemplate.domain.user.service.UserService
import authtemplate.infrastructure.redis.token.service.TokenRedisService

import authtemplate.infrastructure.security.token.core.TokenParser
import authtemplate.infrastructure.security.token.core.TokenProvider

@Component
class AuthUseCase(
    private val userService: UserService,
    private val provider: TokenProvider,
    private val encoder: BCryptPasswordEncoder,
    private val tokenParser: TokenParser,
    private val tokenRedisService: TokenRedisService
) {
    @Transactional
    fun register(request: SignUpRequest): Response {
        userService.validateUserDuplicated(request.username, request.email)
        userService.save(request.toEntity(encoder.encode(request.password)))
        return Response.ok("register successful")
    }

    @Transactional(readOnly = true)
    fun login(request: SignInRequest): DataResponse<TokenResponse> {
        val user: UserEntity = userService.findByUsername(request.username)
        checkIfPasswordIsCorrect(request.password, user.password)
        return DataResponse.ok("login successful", createTokens(user))
    }

    @Transactional(readOnly = true)
    fun refresh(request: RefreshRequest): DataResponse<TokenResponse> {
        val username = tokenParser.findUsername(request.refresh)
        tokenRedisService.checkIfRefreshTokenIsCorrect(request.refresh, username)
        val user: UserEntity = userService.findByUsername(username)
        return DataResponse.ok("refresh token successful", createTokens(user))
    }

    private fun checkIfPasswordIsCorrect(rawPassword: String, encodedPassword: String) {
        if(!encoder.matches(rawPassword, encodedPassword)) throw PasswordNotMatchException()
    }

    private fun createTokens(user: UserEntity): TokenResponse {
        return TokenResponse(
            provider.generateAccess(user),
            provider.generateRefresh(user)
        )
    }
}