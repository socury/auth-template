package authtemplate.infrastructure.common.exception

open class BasicException(
    val statusCode: StatusCode
): RuntimeException()