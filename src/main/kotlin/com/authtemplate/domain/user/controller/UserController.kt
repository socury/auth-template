package com.authtemplate.domain.user.controller

import com.authtemplate.domain.user.dto.request.PatchPasswordRequest
import com.authtemplate.domain.user.dto.request.PutUserRequest
import com.authtemplate.domain.user.dto.response.GetUserResponse
import com.authtemplate.domain.user.service.UserService
import com.authtemplate.global.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.security.Principal

@Tag(name = "User", description = "유저 관련")
@RestController
@RequestMapping("/user")
class UserController (
    private val userService: UserService
) {
    @Operation(summary = "마이페이지")
    @GetMapping("/me")
    fun getMe(principal: Principal): GetUserResponse {
        return userService.getMe(principal)
    }

    @Operation(summary = "유저 조회하기")
    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): GetUserResponse {
        return userService.getUser(userId)
    }

    @Operation(summary = "정보 수정")
    @PutMapping("/me")
    fun putMe(principal: Principal, @RequestBody putUserRequest: PutUserRequest ): GetUserResponse {
        return userService.putMe(principal, putUserRequest)
    }

    @Operation(summary = "패스워드 변경")
    @PatchMapping("/me")
    fun patchPassword(principal: Principal, @RequestBody request: PatchPasswordRequest): BaseResponse<Unit> {
        return userService.patchPassword(principal, request)
    }
}