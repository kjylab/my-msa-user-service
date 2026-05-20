package dev.ktcloud.black.user.adapter.infrastructure.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["dev.ktcloud.black.user.adapter.infrastructure.jpa.repository"])
@EntityScan(basePackages = ["dev.ktcloud.black.user.adapter.infrastructure.jpa.entity"])
class JpaConfig
