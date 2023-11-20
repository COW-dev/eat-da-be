package com.mjucow.eatda.domain.poplarstore.service

import com.mjucow.eatda.persistence.store.StoreRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PopularStoreQueryServiceTest {
    @MockK
    lateinit var mockStoreRepository: StoreRepository

    @MockK
    lateinit var mockCacheService: PopularStoreCacheService

    @InjectMockKs
    lateinit var queryService: PopularStoreQueryService

    @DisplayName("인메모리 캐시가 없다면 데이터를 조회한다")
    @Test
    fun test1() {
        // given
        justRun { mockCacheService.delete(any()) }
        every { mockCacheService.createSearchKey(any()) } returns "notCached"
        every { mockCacheService.getStoresSortByPopular(any()) } returns emptyList()
        every { mockStoreRepository.findAllByIdInOrderByIdDesc(any()) } returns emptyList()

        // when
        queryService.getPopularStores()

        // then
        verify(exactly = 1) { mockCacheService.getStoresSortByPopular(any()) }
        verify(exactly = 1) { mockStoreRepository.findAllByIdInOrderByIdDesc(any()) }
    }

    @DisplayName("인메모리 캐시가 있다면 데이터를 조회하지 않는다")
    @Test
    fun test2() {
        // given
        justRun { mockCacheService.delete(any()) }
        every { mockCacheService.createSearchKey(any()) } returns "notCached"
        every { mockCacheService.getStoresSortByPopular(any()) } returns emptyList()
        every { mockStoreRepository.findAllByIdInOrderByIdDesc(any()) } returns emptyList()

        // when
        repeat(10) {
            queryService.getPopularStores()
        }

        // then
        verify(exactly = 1) { mockCacheService.getStoresSortByPopular(any()) }
        verify(exactly = 1) { mockStoreRepository.findAllByIdInOrderByIdDesc(any()) }
    }
}
