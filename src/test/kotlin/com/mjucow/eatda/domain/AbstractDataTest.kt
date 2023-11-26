package com.mjucow.eatda.domain

import com.mjucow.eatda.AbstractSpringContextTest
import com.mjucow.eatda.common.config.JacksonConfiguration
import com.mjucow.eatda.common.config.JooqContextConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers

@Import(value = [JooqContextConfiguration::class, JacksonConfiguration::class])
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
abstract class AbstractDataTest : AbstractSpringContextTest()
