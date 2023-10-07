package com.mjucow.eatda.domain.store.service.query

import com.mjucow.eatda.domain.store.service.query.dto.StoreDetailDto
import com.mjucow.eatda.domain.store.service.query.dto.StoreDto
import com.mjucow.eatda.persistence.store.StoreRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StoreQueryService(
    val repository: StoreRepository,
) {
    fun findAllByCategoryAndCursor(id: Long? = null, categoryId: Long? = null, page: Pageable): Slice<StoreDto> {
        return if (categoryId == null) {
            repository.findAllByIdLessThanOrderByIdDesc(page, id).map(StoreDto::from)
        } else {
            //FIXME(cache): store 캐시 처리 이후 store 조회 개선하기
            val storeIds = repository.findIdsByCategoryIdOrderByIdDesc(categoryId, page, id)
            val stores = repository.findAllByIdInOrderByIdDesc(storeIds.content).map(StoreDto::from)
            SliceImpl(stores, page, storeIds.hasNext())
        }
    }

    fun findById(storeId: Long): StoreDetailDto? {
        return repository.findByIdOrNull(storeId)?.let { StoreDetailDto.from(it) }
    }
}
