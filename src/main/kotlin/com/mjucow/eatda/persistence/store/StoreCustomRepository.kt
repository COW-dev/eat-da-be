package com.mjucow.eatda.persistence.store

import com.mjucow.eatda.domain.store.entity.Store

interface StoreCustomRepository {
    fun findIdsByCategoryIdOrderByIdDesc(categoryId: Long, size: Int, id: Long? = null): List<Long>
    fun findIdsByCurationIdOrderByIdDesc(curationId: Long, size: Int, id: Long? = null): List<Long>
    fun findAllByIdLessThanOrderByIdDesc(size: Int, id: Long? = null): List<Store>
}
