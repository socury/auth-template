package com.authtemplate.domain.user.dto.request

data class PatchPasswordRequest(
    val currentPassword: String,
    val newPassword: String
)
