package com.example.demo.config

import io.r2dbc.spi.ConnectionFactory
import org.jooq.DSLContext
import org.jooq.conf.RenderQuotedNames
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.springframework.boot.autoconfigure.flyway.FlywayConnectionDetails
import org.springframework.boot.autoconfigure.flyway.FlywayProperties
import org.springframework.boot.autoconfigure.jooq.JooqProperties
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(JooqProperties::class)
class DatabaseConfig {

    @Bean
    fun flywayConnectionDetails(properties: FlywayProperties): FlywayConnectionDetails =
        object : FlywayConnectionDetails {
            override fun getJdbcUrl(): String = properties.url
            override fun getUsername(): String = "postgres"
            override fun getPassword(): String = "postgres"
        }

    @Bean
    fun connectionFactory(properties: R2dbcProperties): ConnectionFactory =
        ConnectionFactoryBuilder.withUrl(properties.url)
            .username("postgres")
            .password("postgres")
            .build()

    @Bean
    fun dslContext(properties: JooqProperties, connectionFactory: ConnectionFactory): DSLContext =
        DSL.using(
            connectionFactory,
            properties.sqlDialect,
            Settings()
                .withBindOffsetTimeType(true)
                .withBindOffsetDateTimeType(true)
                .withRenderQuotedNames(RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED)
        )
}