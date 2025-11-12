package authtemplate.infrastructure.common.exception

interface StatusCode {
    val status: Int
    val message: String
}