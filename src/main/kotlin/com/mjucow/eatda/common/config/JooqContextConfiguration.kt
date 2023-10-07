package com.mjucow.eatda.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.Entity
import org.jooq.Record
import org.jooq.RecordMapper
import org.jooq.RecordMapperProvider
import org.jooq.RecordType
import org.jooq.SQLDialect
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.jooq.impl.DefaultExecuteListenerProvider
import org.jooq.impl.DefaultRecordMapper
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
    private val objectMapper: ObjectMapper,
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
            .withMapConstructorParameterNamesInKotlin(true)

        jooqConfiguration.set(settings)
        jooqConfiguration.set(connectionProvider())
        jooqConfiguration.set(DefaultExecuteListenerProvider(jooqToSpringExceptionTransformer()))
        jooqConfiguration.setSQLDialect(SQLDialect.POSTGRES)
        jooqConfiguration.setRecordMapperProvider(object : RecordMapperProvider {
            override fun <R : Record?, E : Any?> provide(
                recordType: RecordType<R>?,
                type: Class<out E>?,
            ): RecordMapper<R, E> {
                if (type?.annotations?.any { it.annotationClass == Entity::class } == true) {
                    return getEntityRecordMapper(type)
                }

                return DefaultRecordMapper(recordType, type)
            }
        })

        return jooqConfiguration
    }

    @Bean
    fun jooqToSpringExceptionTransformer() = JooqToSpringExceptionTransformer()

    /**
     * JPA Entity의 Secondary Constrcutor로 DefaultRecordMapper가 Mapping되지 않아
     * CustomerRecordMapper로 mapping 해야함. 클래스별로 따로 만들지 않고 map으로 변환 후 objectMapper로 mapping 처리
     */
    private fun <R : Record?, E : Any?> getEntityRecordMapper(type: Class<out E>): RecordMapper<R, E> {
        return RecordMapper<R, E> { objectMapper.convertValue(it!!.intoMap(), type) }
    }
}
