package authtemplate.domain.user.service

import org.springframework.stereotype.Service
import authtemplate.domain.user.entity.UserEntity
import authtemplate.domain.user.exception.UserAlreadyExistsException
import authtemplate.domain.user.exception.UserNotFoundException
import authtemplate.domain.user.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun validateUserDuplicated(username: String, email: String) {
        if(userRepository.findByEmailOrUsername(email, username).isNotEmpty()) throw UserAlreadyExistsException()
    }

    fun save(user: UserEntity)
        = userRepository.save(user)

    fun findByEmail(email: String): UserEntity {
        return userRepository.findByEmail(email) ?: throw UserNotFoundException()
    }

    fun findByUsername(username: String): UserEntity {
        return userRepository.findByUsername(username) ?: throw UserNotFoundException()
    }
}