package authtemplate.infrastructure.security.token.support

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import authtemplate.domain.user.entity.UserDetails
import authtemplate.domain.user.entity.UserEntity

@Component
object UserAuthenticationHolder {
    fun current(): UserEntity {
        return (SecurityContextHolder.getContext().authentication.principal as UserDetails).user
    }
}