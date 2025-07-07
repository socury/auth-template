package authtemplate.domain.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import authtemplate.domain.user.entity.UserEntity
import java.util.*

@Repository
interface UserRepository: JpaRepository<UserEntity, UUID> {
    fun findByEmail(email: String): UserEntity?
    fun findByUsername(username: String): UserEntity?
    fun findByEmailOrUsername(email: String, username: String): List<UserEntity>
}