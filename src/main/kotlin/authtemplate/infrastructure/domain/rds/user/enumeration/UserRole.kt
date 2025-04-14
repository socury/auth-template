package authtemplate.infrastructure.domain.rds.user.enumeration

enum class UserRole(val value: String) {
    MEMBER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
}