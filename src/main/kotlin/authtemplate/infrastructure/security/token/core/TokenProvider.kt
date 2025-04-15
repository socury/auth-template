package authtemplate.infrastructure.security.token.core

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import authtemplate.application.support.token.enumeration.TokenType
import authtemplate.infrastructure.domain.rds.user.entity.UserEntity
import authtemplate.infrastructure.domain.redis.token.service.TokenRedisService
import authtemplate.infrastructure.security.token.properties.TokenProperties
import java.lang.System.currentTimeMillis
import java.util.*

@Component
class TokenProvider(
    private val properties: TokenProperties,
    private val tokenRedisService: TokenRedisService
) {
    private fun generate(tokenType: TokenType, member: UserEntity, expire: Long): String {
        return Jwts.builder()
            .claim("category", tokenType.value)
            .claim("username", member.username)
            .claim("role", member.role)
            .issuedAt(Date(currentTimeMillis()))
            .expiration(Date(currentTimeMillis() + expire))
            .signWith(properties.secretKey())
            .compact()
    }

    fun generateAccess(member: UserEntity): String
        = generate(TokenType.ACCESS_TOKEN, member, properties.access)

    fun generateRefresh(member: UserEntity): String {
        val refreshToken = generate(TokenType.REFRESH_TOKEN, member, properties.refresh)
        tokenRedisService.storeRefreshToken(member.username, refreshToken)
        return refreshToken
    }
}