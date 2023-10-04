package com.mjucow.eatda.persistence.store

import com.mjucow.eatda.domain.store.entity.Store
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository : JpaRepository<Store, Long> {
    fun findByIdLessThanOrderByIdDesc(id: Long, page: Pageable): Slice<Store>
    fun existsByName(name: String): Boolean
}
