package com.mjucow.eatda.domain.poplarstore.service

import com.mjucow.eatda.domain.AbstractCacheTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.StringRedisTemplate
import java.time.LocalDateTime
import java.time.ZoneOffset

@Import(value = [PopularStoreCacheService::class])
class PopularStoreCacheServiceTest : AbstractCacheTest() {

    @Autowired
    lateinit var stringRedisTemplate: StringRedisTemplate

    @Autowired
    lateinit var cacheService: PopularStoreCacheService

    @DisplayName("새로운 키로 저장하면 count가 1이어야 한다.")
    @Test
    fun setTest1() {
        // given
        val storedAt = LocalDateTime.of(2023, 1, 1, 1, 1).toInstant(ZoneOffset.UTC)
        val storeId = 1L

        // when
        cacheService.setStore(storedAt, storeId)

        // then
        val searchKey = cacheService.createSearchKey(storedAt.plusSeconds(UNIT_MINUTE * 60))
        val count = stringRedisTemplate.opsForZSet().score(searchKey, storeId.toString())
        assertThat(count?.toInt()).isEqualTo(1)
    }

    @DisplayName("저장된 키라면 count가 +1 되어야 한다.")
    @Test
    fun setTest2() {
        // given
        val storedAt = LocalDateTime.of(2023, 1, 2, 1, 1).toInstant(ZoneOffset.UTC)
        val storeId = 1L
        val expectCount = 10

        // when
        repeat(expectCount) {
            cacheService.setStore(storedAt, storeId)
        }

        // then
        val searchKey = cacheService.createSearchKey(storedAt.plusSeconds(UNIT_MINUTE * 60))
        val count = stringRedisTemplate.opsForZSet().score(searchKey, storeId.toString())
        assertThat(count?.toInt()).isEqualTo(expectCount)
    }

    @DisplayName("캐시된 데이터가 없다면 빈 배열이 반환된다")
    @Test
    fun getTest1() {
        // given
        val key = "notFoundKey"

        // when
        val popularStores = cacheService.getStoresSortByPopular(key)

        // then
        assertThat(popularStores).isEmpty()
    }

    @DisplayName("캐시된 데이터가 있다면 배열을 반환한다.")
    @Test
    fun getTest2() {
        // given
        val storedAt = LocalDateTime.of(2023, 2, 1, 1, 1).toInstant(ZoneOffset.UTC)
        val searchKey = cacheService.createSearchKey(storedAt.plusSeconds(UNIT_MINUTE * 60))
        val cachedStoreSize = (MAX_POPULAR_STORE_SIZE - 1).toInt()
        repeat(cachedStoreSize) {
            cacheService.setStore(storedAt, it.toLong())
        }

        // when
        val popularStores = cacheService.getStoresSortByPopular(searchKey)

        // then
        assertThat(popularStores.size).isEqualTo(cachedStoreSize)
    }

    @DisplayName("캐시된 데이터가 최대 사이즈보다 많다면 최대 사이즈만큼 배열을 반환한다.")
    @Test
    fun getTest3() {
        // given
        val storedAt = LocalDateTime.of(2023, 2, 2, 1, 1).toInstant(ZoneOffset.UTC)
        val searchKey = cacheService.createSearchKey(storedAt.plusSeconds(UNIT_MINUTE * 60))
        val cachedStoreSize = (MAX_POPULAR_STORE_SIZE * 2).toInt()
        repeat(cachedStoreSize) {
            cacheService.setStore(storedAt, it.toLong())
        }

        // when
        val popularStores = cacheService.getStoresSortByPopular(searchKey)

        // then
        assertThat(popularStores.size).isEqualTo(MAX_POPULAR_STORE_SIZE)
    }

    @DisplayName("캐시된 데이터는 카운트가 높은 순서대로 정렬되어 나와야 한다")
    @Test
    fun getTest4() {
        // given
        val storedAt = LocalDateTime.of(2023, 2, 3, 1, 1).toInstant(ZoneOffset.UTC)
        val searchKey = cacheService.createSearchKey(storedAt.plusSeconds(UNIT_MINUTE * 60))
        val cachedStoreSize = (MAX_POPULAR_STORE_SIZE * 2).toInt()
        repeat(cachedStoreSize) {storeId ->
            repeat(storeId) {
                cacheService.setStore(storedAt, storeId.toLong())
            }
        }

        // when
        val popularStores = cacheService.getStoresSortByPopular(searchKey)

        // then
        assertThat(popularStores)
            .containsExactlyInAnyOrderElementsOf(popularStores.sortedByDescending { it.count })
    }

    companion object {
        const val MAX_POPULAR_STORE_SIZE = PopularStoreCacheService.MAX_SIZE
        const val UNIT_MINUTE = PopularStoreCacheService.UNIT_MINUTE
    }
}
