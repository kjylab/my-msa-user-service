package dev.ktcloud.black.user.application.dto

import dev.ktcloud.black.user.domain.vo.UserRole
import java.util.UUID

data class UserDto(
    val id: UUID,
    val role: UserRole,
    val email: String,
    val name: String,
)
