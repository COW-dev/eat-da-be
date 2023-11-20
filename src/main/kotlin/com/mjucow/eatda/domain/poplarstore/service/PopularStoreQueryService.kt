package com.mjucow.eatda.domain.poplarstore.service

import com.mjucow.eatda.domain.poplarstore.service.dto.PopularStoreDto
import com.mjucow.eatda.domain.poplarstore.service.dto.PopularStoreDtos
import com.mjucow.eatda.persistence.store.StoreRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class PopularStoreQueryService(
    private val cache: PopularStoreCacheService,
    private val storeRepository: StoreRepository,
) {
    private var cachedPopularStoresKey: String = ""
    private var cachedPopularStoresValue: PopularStoreDtos = PopularStoreDtos(emptyList())

    fun getPopularStores(searchAt: Instant = Instant.now()) : PopularStoreDtos {
        val searchKey = cache.createSearchKey(searchAt)
        if (searchKey != cachedPopularStoresKey) {
            val popularStores = cache.getStoresSortByPopular(searchKey)
            val stores = storeRepository.findAllByIdInOrderByIdDesc(popularStores.map { it.storeId })
            val popularStoreDtos = PopularStoreDtos(
                popularStores = popularStores.mapNotNull { ps ->
                    val entity = stores.firstOrNull { it.id == ps.storeId }
                    if (entity != null) PopularStoreDto.of(ps, entity) else null
                }
            )

            cachedPopularStoresKey = searchKey
            cachedPopularStoresValue = popularStoreDtos
        }

        return cachedPopularStoresValue
    }
}
