package com.mjucow.eatda.persistence.store

import com.mjucow.eatda.domain.store.entity.Store
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface StoreCustomRepository {
    fun findIdsByCategoryIdOrderByIdDesc(categoryId: Long, page: Pageable, id: Long? = null): Slice<Long>
    fun findAllByIdLessThanOrderByIdDesc(page: Pageable, id: Long? = null): Slice<Store>
}
