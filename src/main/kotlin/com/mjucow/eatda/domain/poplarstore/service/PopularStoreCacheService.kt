package com.mjucow.eatda.domain.poplarstore.service

import com.mjucow.eatda.domain.poplarstore.entity.PopularStore
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class PopularStoreCacheService(
    private val redisTemplate: StringRedisTemplate,
) {
    fun getStoresSortByPopular(key: String): List<PopularStore> {
        return redisTemplate.opsForZSet()
            .rangeWithScores(key, 0, MAX_SIZE)
            ?.mapNotNull {
                if (it.value != null && it.score != null) {
                    PopularStore(it.value!!.toLong(), it.score!!.toLong())
                } else {
                    null
                }
            } ?: emptyList<PopularStore>()
            .sortedByDescending { it.count }
    }

    fun setStore(storedAt: Instant, storeId: Long) {
        val ops = redisTemplate.opsForZSet()
        ops.incrementScore(createKey(storedAt), storeId.toString(), 1.0)
    }

    /**
     * 검색 키는 이전 AreaKey를 생성한다.
     */
    fun createSearchKey(searchAt: Instant): String {
        return createKey(searchAt.minusSeconds(UNIT_MINUTE * 60))
    }

    /**
     * area key = yyyyMMdd-hh-{area-number}
     * prefix: yyyyMMdd-hh- ex) 20231119-23
     * area-number: 0,1,2,3 (unit minute = 15)
     */
    private fun createKey(createdAt: Instant): String {
        val localDateTime = LocalDateTime.ofInstant(createdAt, ZoneId.of("Asia/Seoul"))

        val formatDateHour = localDateTime.format(DEFAULT_FORMATTER)
        val unitMinute = localDateTime.minute / UNIT_MINUTE

        val areaKey = "$formatDateHour-$unitMinute"
        return "${PREFIX}-${areaKey}"
    }

    companion object {
        val DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd-HH")!!
        const val PREFIX = "POPULAR_STORE"
        const val UNIT_MINUTE = 15L
        const val MAX_SIZE = 10L
    }
}
