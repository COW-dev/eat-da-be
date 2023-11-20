package com.mjucow.eatda.domain.store.service.query

import com.mjucow.eatda.domain.poplarstore.service.PopularStoreCommandService
import com.mjucow.eatda.domain.store.service.query.dto.StoreDetailDto
import com.mjucow.eatda.domain.store.service.query.dto.StoreDto
import com.mjucow.eatda.persistence.store.StoreRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StoreQueryService(
    val repository: StoreRepository,
    val popularStoreCommandService: PopularStoreCommandService,
) {
    fun findAllByCategoryAndCursor(id: Long? = null, categoryId: Long? = null, page: Pageable): Slice<StoreDto> {
        return if (categoryId == null) {
            repository.findAllByIdLessThanOrderByIdDesc(page, id).map(StoreDto::from)
        } else {
            // FIXME(cache): store 캐시 처리 이후 store 조회 개선하기
            val storeIds = repository.findIdsByCategoryIdOrderByIdDesc(categoryId, page, id)
            val stores = repository.findAllByIdInOrderByIdDesc(storeIds.content).map(StoreDto::from)
            SliceImpl(stores, page, storeIds.hasNext())
        }
    }

    fun findById(storeId: Long): StoreDetailDto {
        // FIXME(async): findById가 popular store command 과정에서 실패하지 않도록 비동기 호출 처리
        popularStoreCommandService.setStore(storeId)
        return StoreDetailDto.from(repository.getReferenceById(storeId))
    }
}
