package com.mjucow.eatda.domain.poplarstore.service

import org.springframework.stereotype.Service
import java.time.Instant

@Service
class PopularStoreCommandService(
    private val cache: PopularStoreCacheService,
) {
    fun setStore(storeId: Long) {
        cache.setStore(Instant.now(), storeId)
    }
}
