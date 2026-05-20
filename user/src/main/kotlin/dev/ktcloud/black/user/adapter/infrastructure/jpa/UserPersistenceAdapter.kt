package dev.ktcloud.black.user.adapter.infrastructure.jpa

import dev.ktcloud.black.user.adapter.infrastructure.jpa.entity.UserJpaEntity
import dev.ktcloud.black.user.adapter.infrastructure.jpa.repository.UserJpaRepository
import dev.ktcloud.black.user.application.port.outbound.UserCommandOutboundPort
import dev.ktcloud.black.user.application.port.outbound.UserQueryOutboundPort
import dev.ktcloud.black.user.domain.entity.UserDomainEntity
import dev.ktcloud.black.user.domain.exception.UserException
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository,
) : UserQueryOutboundPort, UserCommandOutboundPort {

    override fun findById(id: UUID): UserDomainEntity =
        userJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElseThrow { UserException.UserNotFoundException() }

    override fun findByEmail(email: String): UserDomainEntity =
        userJpaRepository.findByEmail(email)?.toDomain()
            ?: throw UserException.UserNotFoundException()

    override fun existsByEmail(email: String): Boolean =
        userJpaRepository.existsByEmail(email)

    override fun save(user: UserDomainEntity): UserDomainEntity =
        userJpaRepository.save(UserJpaEntity.fromDomain(user)).toDomain()
}
