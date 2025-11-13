package authtemplate.infrastructure.redis.token.service

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import authtemplate.infrastructure.security.exception.RefreshTokenNotMatchException
import authtemplate.infrastructure.security.token.properties.TokenProperties
import java.util.concurrent.TimeUnit

@Service
class TokenRedisService (
    private val redisTemplate: StringRedisTemplate,
    private val tokenProperties: TokenProperties
) {
    fun storeRefreshToken(key: String, refreshToken: String) {
        redisTemplate.opsForValue().set("refresh_token:$key", refreshToken, tokenProperties.refresh, TimeUnit.MILLISECONDS)
    }

    fun getRefreshToken(key: String): String? {
        return redisTemplate.opsForValue().get("refresh_token:$key")
    }

    fun checkIfRefreshTokenIsCorrect(refreshToken: String, key: String)  {
        if (refreshToken != getRefreshToken(key))
            throw RefreshTokenNotMatchException()
    }
}