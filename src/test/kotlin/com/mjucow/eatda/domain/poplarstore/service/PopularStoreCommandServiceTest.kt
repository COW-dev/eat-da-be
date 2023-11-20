package com.mjucow.eatda.domain.poplarstore.service

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PopularStoreCommandServiceTest {

    @MockK
    lateinit var mockCacheService: PopularStoreCacheService

    @InjectMockKs
    lateinit var commandService: PopularStoreCommandService

    @DisplayName("데이터를 저장하면 캐시에 저장된다")
    @Test
    fun test1() {
        // given
        justRun { mockCacheService.setStore(any(), any()) }

        // when
        commandService.setStore(1L)

        // then
        verify { mockCacheService.setStore(any(), any()) }
    }
}
