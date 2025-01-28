package com.authtemplate.domain.user.service

import com.authtemplate.domain.user.dto.request.PatchPasswordRequest
import com.authtemplate.domain.user.dto.request.PutUserRequest
import com.authtemplate.domain.user.dto.response.GetUserResponse
import com.authtemplate.domain.user.entity.UserEntity
import com.authtemplate.domain.user.exception.UserErrorCode
import com.authtemplate.domain.user.repository.UserRepository
import com.authtemplate.global.dto.BaseResponse
import com.authtemplate.global.exception.CustomException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class UserService (
    private val userRepository: UserRepository
) {
    fun formUser(user: UserEntity): GetUserResponse{
        return GetUserResponse(
            id = user.id,
            username = user.username
        )
    }

    fun getMe(principal: Principal): GetUserResponse {
        val user = userRepository.findByUsername(principal.name).orElseThrow{ CustomException(UserErrorCode.USER_NOT_FOUND) }
        return formUser(user)
    }

    fun getUser(userId: Long): GetUserResponse {
        val user = userRepository.findById(userId).orElseThrow{CustomException(UserErrorCode.USER_NOT_FOUND)}
        return formUser(user)
    }

    fun putMe(principal: Principal, putUserRequest: PutUserRequest): GetUserResponse {
        val user = userRepository.findByUsername(principal.name).orElseThrow{ CustomException(UserErrorCode.USER_NOT_FOUND) }

        user.username = putUserRequest.username

        userRepository.save(user)
        return formUser(user)
    }

    fun patchPassword(principal: Principal, request: PatchPasswordRequest): BaseResponse<Unit> {
        val user = userRepository.findByUsername(principal.name).orElseThrow{ CustomException(UserErrorCode.USER_NOT_FOUND) }
        if (user.password != BCryptPasswordEncoder().encode(request.currentPassword))
            throw CustomException(UserErrorCode.USER_NOT_MATCH)

        user.password = BCryptPasswordEncoder().encode(request.newPassword)
        userRepository.save(user)

        return BaseResponse(
            message = "패스워드 변경에 성공했습니다."
        )
    }
}