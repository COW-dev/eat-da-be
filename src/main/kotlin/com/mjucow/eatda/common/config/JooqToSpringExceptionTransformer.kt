package com.mjucow.eatda.common.config

import org.jooq.ExecuteContext
import org.jooq.ExecuteListener
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator

class JooqToSpringExceptionTransformer : ExecuteListener {
    override fun exception(ctx: ExecuteContext) {
        if (ctx.sqlException() == null) return

        val dialect = ctx.configuration().dialect()
        val translator = SQLErrorCodeSQLExceptionTranslator(dialect.name)

        ctx.exception(translator.translate("jOOQ", ctx.sql(), ctx.sqlException()!!))
    }
}
