package dev.ktcloud.black.user.adapter.infrastructure.jpa.entity

import dev.ktcloud.black.user.domain.entity.UserDomainEntity
import dev.ktcloud.black.user.domain.vo.UserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users")
class UserJpaEntity(
    @Id val id: UUID,
    @Column(nullable = false, unique = true) val email: String,
    @Column(nullable = false) val password: String,
    @Column(nullable = false) val name: String,
    @Enumerated(EnumType.STRING) @Column(nullable = false) val role: UserRole,
) {
    fun toDomain() = UserDomainEntity(
        id = id, role = role, email = email, password = password, name = name,
    )

    companion object {
        fun fromDomain(user: UserDomainEntity) = UserJpaEntity(
            id = user.id, email = user.email, password = user.password, name = user.name, role = user.role,
        )
    }
}
