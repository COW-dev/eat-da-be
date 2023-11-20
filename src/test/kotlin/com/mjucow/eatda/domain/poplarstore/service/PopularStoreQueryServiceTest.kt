package com.mjucow.eatda.domain.poplarstore.service

import com.mjucow.eatda.domain.poplarstore.entity.PopularStore
import com.mjucow.eatda.domain.store.entity.objectmother.StoreMother
import com.mjucow.eatda.persistence.store.StoreRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions
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

    @DisplayName("캐시에 있지만 영속화되어 있지 않은 가게라면 조회되지 않는다 ")
    @Test
    fun test3() {
        // given
        every { mockCacheService.createSearchKey(any()) } returns "searchKey"
        every { mockCacheService.getStoresSortByPopular(any()) } returns listOf(PopularStore(1L, 1L))
        every { mockStoreRepository.findAllByIdInOrderByIdDesc(any()) } returns emptyList()

        // when
        val result = queryService.getPopularStores()

        // then
        Assertions.assertThat(result.popularStores).isEmpty()
    }

    @DisplayName("캐시있고 영속화되어 있는 가게라면 조회된다 ")
    @Test
    fun test4() {
        // given
        val entityId = 1L
        every { mockCacheService.createSearchKey(any()) } returns "searchKey"
        every { mockCacheService.getStoresSortByPopular(any()) } returns listOf(
            PopularStore(entityId, 1)
        )
        every { mockStoreRepository.findAllByIdInOrderByIdDesc(any()) } returns listOf(
            StoreMother.createWithId(id = entityId)
        )

        // when
        val result = queryService.getPopularStores()

        // then
        Assertions.assertThat(result.popularStores.size).isEqualTo(1)
    }
}
