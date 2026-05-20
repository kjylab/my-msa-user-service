package dev.ktcloud.black.user.adapter.infrastructure.jpa.repository

import dev.ktcloud.black.user.adapter.infrastructure.jpa.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserJpaRepository : JpaRepository<UserJpaEntity, UUID> {
    fun findByEmail(email: String): UserJpaEntity?
    fun existsByEmail(email: String): Boolean
}
