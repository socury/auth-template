package authtemplate.infrastructure.security.token.core

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.stereotype.Component
import authtemplate.application.support.token.enumeration.TokenType
import authtemplate.infrastructure.security.token.exception.FakeTokenException
import authtemplate.infrastructure.security.token.exception.InvalidTokenException
import authtemplate.infrastructure.security.token.exception.TokenExpiredException
import java.util.*

@Component
class TokenValidator(
    private val parser: TokenParser,
) {
    fun validateAll(token: String, tokenType: TokenType) {
        validate(token)
        validateType(token, tokenType)
    }

    fun validateType(token: String, tokenType: TokenType) {
        if (parser.findType(token) != tokenType) throw FakeTokenException()
    }

    fun validate(token: String) {
        try {
            parser.findType(token)
            if (
                parser.findExpiration(token).before(Date())
            ) throw TokenExpiredException()
        } catch (e: ExpiredJwtException) {
            throw TokenExpiredException()
        } catch (e: SignatureException) {
            throw FakeTokenException()
        } catch (e: Exception) {
            throw InvalidTokenException()
        }
    }
}