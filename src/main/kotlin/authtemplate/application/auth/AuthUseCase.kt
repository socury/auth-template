package pang.pangserver.application.auth

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import authtemplate.application.auth.data.request.RefreshRequest
import authtemplate.application.auth.data.request.SignInRequest
import authtemplate.application.auth.data.request.SignUpRequest
import authtemplate.application.auth.data.response.TokenResponse
import authtemplate.application.support.data.DataResponse
import authtemplate.application.support.data.Response
import authtemplate.infrastructure.domain.rds.user.entity.UserEntity
import authtemplate.infrastructure.domain.rds.user.exception.PasswordNotMatchException
import authtemplate.infrastructure.domain.rds.user.service.UserService
import pang.pangserver.infrastructure.domain.redis.token.service.TokenRedisService
import authtemplate.infrastructure.security.token.core.TokenParser
import authtemplate.infrastructure.security.token.core.TokenProvider

@Component
@Transactional(rollbackFor = [Exception::class])
class AuthUseCase(
    private val userService: UserService,
    private val provider: TokenProvider,
    private val encoder: BCryptPasswordEncoder,
    private val tokenParser: TokenParser,
    private val tokenRedisService: TokenRedisService
) {
    fun register(request: SignUpRequest): Response {
        userService.validateMemberDuplicated(request.username, request.email)
        userService.save(request.toEntity(encoder.encode(request.password)))
        return Response.ok("register successful")
    }

    @Transactional(readOnly = true)
    fun login(request: SignInRequest): DataResponse<TokenResponse> {
        val user: UserEntity = userService.findByEmail(request.email)
        checkIfPasswordIsCorrect(request.password, user.password)
        return DataResponse.ok("login successful", createTokens(user))
    }

    fun refresh(request: RefreshRequest): DataResponse<TokenResponse> {
        val member: UserEntity = userService.findByEmail(tokenParser.findUsername(request.refresh))
        tokenRedisService.checkIfRefreshTokenIsCorrect(request.refresh, member.username)
        return DataResponse.ok("refresh token successful", createTokens(member))
    }

    private fun checkIfPasswordIsCorrect(rawPassword: String, encodedPassword: String) {
        if(encoder.matches(rawPassword, encodedPassword)) throw PasswordNotMatchException()
    }

    private fun createTokens(member: UserEntity): TokenResponse {
        return TokenResponse(
            provider.generateAccess(member),
            provider.generateRefresh(member)
        )
    }
}