package authtemplate.domain.user.entity

import authtemplate.application.user.data.request.UpdateUserRequest
import authtemplate.domain.support.entity.BasicEntity
import authtemplate.domain.user.enumeration.UserRole
import jakarta.persistence.*
import java.util.*

@Entity(name = "users")
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(columnDefinition = "text")
    var password: String,

    var role: UserRole,
): BasicEntity() {
    fun update(request: UpdateUserRequest) {
        this.username = request.username
        this.email = request.email
    }
}