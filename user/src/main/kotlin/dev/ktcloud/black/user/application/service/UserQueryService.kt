package dev.ktcloud.black.user.application.service

import dev.ktcloud.black.user.application.port.inbound.FetchMeQuery
import dev.ktcloud.black.user.application.port.outbound.UserQueryOutboundPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserQueryService(
    private val userQueryOutboundPort: UserQueryOutboundPort,
) : FetchMeQuery {

    @Transactional(readOnly = true)
    override fun fetchMe(query: FetchMeQuery.In): FetchMeQuery.Out {
        val user = userQueryOutboundPort.findById(query.id)
        return FetchMeQuery.Out.from(user)
    }
}
