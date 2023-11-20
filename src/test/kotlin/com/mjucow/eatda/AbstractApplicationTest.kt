package com.mjucow.eatda

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
@ActiveProfiles("test")
abstract class AbstractApplicationTest {
}
