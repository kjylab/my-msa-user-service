package dev.ktcloud.black.userservice.adapter.inbound.rest

import dev.ktcloud.black.user.adapter.presentation.web.inbound.UserRestController
import dev.ktcloud.black.user.adapter.presentation.web.inbound.request.CreateUserRequest
import dev.ktcloud.black.user.adapter.presentation.web.inbound.request.FetchMeRequest
import dev.ktcloud.black.user.adapter.presentation.web.inbound.response.CreateUserResponse
import dev.ktcloud.black.user.adapter.presentation.web.inbound.response.FetchMeResponse
import dev.ktcloud.black.user.application.dto.UserDto
import dev.ktcloud.black.user.application.port.inbound.CreateUserCommand
import dev.ktcloud.black.user.application.port.inbound.FetchMeQuery
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/users")
class UserRestControllerAdapter(
    private val createUserCommand: CreateUserCommand,
    private val fetchMeQuery: FetchMeQuery,
) : UserRestController {

    @PostMapping
    override fun createUser(@RequestBody req: CreateUserRequest): CreateUserResponse {
        val out = createUserCommand.create(
            CreateUserCommand.In(
                email = req.email,
                plainPassword = req.plainPassword,
                name = req.name,
            )
        )
        return CreateUserResponse(
            profile = UserDto(id = out.id, role = out.role, email = out.email, name = out.name)
        )
    }

    @PostMapping("/me")
    override fun fetchMe(@RequestBody req: FetchMeRequest): FetchMeResponse {
        val out = fetchMeQuery.fetchMe(FetchMeQuery.In(UUID.fromString(req.id)))
        return FetchMeResponse(
            profile = UserDto(id = out.id, role = out.role, email = out.email, name = out.name)
        )
    }
}
