package com.mjucow.eatda.persistence.store

import com.mjucow.eatda.domain.store.entity.Store
import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository : JpaRepository<Store, Long>, StoreCustomRepository {
    fun existsByName(name: String): Boolean
    fun findAllByIdInOrderByIdDesc(id: List<Long>): List<Store>
}
