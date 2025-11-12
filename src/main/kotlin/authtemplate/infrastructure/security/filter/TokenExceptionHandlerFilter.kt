package authtemplate.infrastructure.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import authtemplate.application.support.data.ErrorResponseSender
import authtemplate.application.support.exception.GlobalStatusCode
import authtemplate.infrastructure.common.exception.BasicException
import org.hibernate.query.sqm.tree.SqmNode.log

@Component
class TokenExceptionHandlerFilter(
    private val errorSender: ErrorResponseSender
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: BasicException) {
            errorSender.send(response, e.statusCode)
        } catch (e: Exception) {
            log.error(e.message, e)
            errorSender.send(response, GlobalStatusCode.INTERNAL_SERVER_ERROR)
        }
    }
}