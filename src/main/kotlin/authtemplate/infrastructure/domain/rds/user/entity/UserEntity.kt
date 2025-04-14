package authtemplate.infrastructure.domain.rds.user.entity

import jakarta.persistence.*
import authtemplate.infrastructure.domain.rds.user.enumeration.UserRole
import authtemplate.infrastructure.domain.rds.support.entity.BasicEntity
import java.util.*

@Entity(name = "users")
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(columnDefinition = "text")
    var password: String,

    val role: UserRole,
): BasicEntity()