package com.authtemplate.domain.user.controller

import com.authtemplate.domain.user.dto.response.GetUserResponse
import com.authtemplate.domain.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
}