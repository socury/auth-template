package authtemplate.infrastructure.security.filter

import authtemplate.application.support.token.enumeration.TokenType
import authtemplate.domain.rds.user.entity.UserDetails
import authtemplate.domain.rds.user.exception.UserNotFoundException
import authtemplate.domain.rds.user.repository.UserRepository
import authtemplate.infrastructure.security.token.core.TokenParser
import authtemplate.infrastructure.security.token.core.TokenValidator
import authtemplate.infrastructure.security.token.exception.EmptyTokenException
import authtemplate.infrastructure.security.token.exception.InvalidTokenException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class TokenFilter (
    private val tokenParser: TokenParser,
    private val tokenValidator: TokenValidator,
    private val memberRepository: UserRepository
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.getHeader("Authorization").isNullOrEmpty()) {
            filterChain.doFilter(request, response)
            return
        }
        val bearerToken: String = request.getHeader("Authorization")

        if (!bearerToken.startsWith("Bearer "))
            throw InvalidTokenException()

        val token: String = bearerToken.removePrefix("Bearer ")
        tokenValidator.validateAll(token, TokenType.ACCESS_TOKEN)
        setAuthentication(token)

        filterChain.doFilter(request, response)
    }

    private fun setAuthentication(token: String) {
        val member = getMemberDetails(token)
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(member, null, member.authorities)
    }

    private fun getMemberDetails(token: String): UserDetails {
        return UserDetails(memberRepository.findByUsername(tokenParser.findUsername(token))?: throw UserNotFoundException())
    }
}