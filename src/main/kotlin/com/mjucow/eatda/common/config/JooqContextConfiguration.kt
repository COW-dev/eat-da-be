package com.mjucow.eatda.common.config

import org.jooq.SQLDialect
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.jooq.impl.DefaultExecuteListenerProvider
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@ImportAutoConfiguration(JooqAutoConfiguration::class)
class JooqContextConfiguration(
    private val dataSource: DataSource,
) {

    @Bean
    fun connectionProvider() = DataSourceConnectionProvider(TransactionAwareDataSourceProxy(dataSource))

    @Bean
    fun dsl() = DefaultDSLContext(configuration())

    fun configuration(): DefaultConfiguration {
        val jooqConfiguration = DefaultConfiguration()

        val settings = jooqConfiguration.settings()
            .withExecuteWithOptimisticLocking(true)
            .withExecuteLogging(true)

        jooqConfiguration.set(settings)
        jooqConfiguration.set(connectionProvider())
        jooqConfiguration.set(DefaultExecuteListenerProvider(jooqToSpringExceptionTransformer()))
        jooqConfiguration.setSQLDialect(SQLDialect.POSTGRES)

        return jooqConfiguration
    }

    @Bean
    fun jooqToSpringExceptionTransformer() = JooqToSpringExceptionTransformer()

}

