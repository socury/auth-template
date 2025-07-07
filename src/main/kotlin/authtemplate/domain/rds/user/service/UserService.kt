package authtemplate.domain.rds.user.service

import org.springframework.stereotype.Service
import authtemplate.domain.rds.user.entity.UserEntity
import authtemplate.domain.rds.user.exception.UserAlreadyExistsException
import authtemplate.domain.rds.user.exception.UserNotFoundException
import authtemplate.domain.rds.user.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun validateMemberDuplicated(username: String, email: String) {
        if(userRepository.findByEmailOrUsername(email, username).isNotEmpty()) throw UserAlreadyExistsException()
    }

    fun save(member: UserEntity)
        = userRepository.save(member)

    fun findByEmail(email: String): UserEntity {
        return userRepository.findByEmail(email) ?: throw UserNotFoundException()
    }

    fun findByUsername(username: String): UserEntity {
        return userRepository.findByUsername(username) ?: throw UserNotFoundException()
    }
}