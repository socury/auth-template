package authtemplate.domain.user.exception

import authtemplate.infrastructure.common.exception.BasicException

class UserNotFoundException(): BasicException(UserStatusCode.USER_NOT_FOUND)
class PasswordNotMatchException(): BasicException(UserStatusCode.PASSWORD_NOT_MATCH)
class UserAlreadyExistsException(): BasicException(UserStatusCode.USER_ALREADY_EXISTS)