package com.mjucow.eatda.presentation.store.popularstore

import com.mjucow.eatda.domain.poplarstore.service.PopularStoreCacheService
import com.mjucow.eatda.domain.poplarstore.service.PopularStoreQueryService
import com.mjucow.eatda.domain.poplarstore.service.dto.PopularStoreDtos
import com.mjucow.eatda.presentation.common.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RequestMapping("/api/v1/stores/popular")
@RestController
class PopularStoreController(
    private val cacheService: PopularStoreCacheService,
    private val queryService: PopularStoreQueryService,
) : PopularStoreApiPresentation {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    override fun findAllPopularStore(): ApiResponse<PopularStoreDtos> {
        return ApiResponse.success(queryService.getPopularStores())
    }

    @PostMapping("/cache/{storeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun setRedis(@PathVariable storeId: String) {
        cacheService.setStore(Instant.now(), storeId.toLong())
    }

    @GetMapping("/cache/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    fun getRedis(@PathVariable storeId: String): ApiResponse<RedisCache?> {
        val key = cacheService.createSearchKey(Instant.now())
        val popularStores = cacheService.getStoresSortByPopular(key)
        if (popularStores.isEmpty()) {
            return ApiResponse.success(null)
        } else {
            val e = popularStores[0]
            return ApiResponse.success(RedisCache(e.storeId, e.count))
        }
    }

    data class RedisCache(val storeId: Long, val count: Long)
}
