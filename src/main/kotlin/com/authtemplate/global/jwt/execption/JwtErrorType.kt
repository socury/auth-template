package com.authtemplate.global.jwt.execption

enum class JwtErrorType {
    OK,
    ExpiredJwtException,
    SignatureException,
    MalformedJwtException,
    UnsupportedJwtException,
    IllegalArgumentException,
    UNKNOWN_EXCEPTION
}