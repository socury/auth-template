package com.authtemplate.domain.user.service

import com.authtemplate.domain.user.exception.UserErrorCode
import com.authtemplate.domain.user.repository.UserRepository
import com.authtemplate.global.exception.CustomException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService (
    val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username).orElseThrow { CustomException(UserErrorCode.USER_NOT_FOUND) }
        return org.springframework.security.core.userdetails.User(user.username, user.password, emptyList())
    }
}