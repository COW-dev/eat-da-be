package com.mjucow.eatda.domain.store.service.query

import com.mjucow.eatda.domain.store.service.query.dto.StoreDetailDto
import com.mjucow.eatda.domain.store.service.query.dto.StoreDto
import com.mjucow.eatda.persistence.store.StoreRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StoreQueryService(
    val repository: StoreRepository,
) {
    fun findAllByCursor(id: Long, page: Pageable): Slice<StoreDto> {
        return repository.findByIdLessThanOrderByIdDesc(id, page).map(StoreDto::from)
    }

    fun findById(storeId: Long): StoreDetailDto? {
        return repository.findByIdOrNull(storeId)?.let { StoreDetailDto.from(it) }
    }
}
