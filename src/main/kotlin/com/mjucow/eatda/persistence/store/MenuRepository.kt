package com.mjucow.eatda.persistence.store

import com.mjucow.eatda.domain.store.entity.Menu
import org.springframework.data.jpa.repository.JpaRepository

interface MenuRepository : JpaRepository<Menu, Long> {
    fun findAllByStoreId(storeId: Long) : List<Menu>
}
