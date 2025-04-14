package authtemplate.infrastructure.security.token.support

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import authtemplate.infrastructure.domain.rds.user.entity.UserDetails
import authtemplate.infrastructure.domain.rds.user.entity.UserEntity

@Component
object UserAuthenticationHolder {
    fun current(): UserEntity {
        return (SecurityContextHolder.getContext().authentication.principal as UserDetails).user
    }
}