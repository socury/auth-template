package authtemplate.core.exception

open class BasicException(
    val statusCode: StatusCode
): RuntimeException()