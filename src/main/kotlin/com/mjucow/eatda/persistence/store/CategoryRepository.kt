package com.mjucow.eatda.persistence.store

import com.mjucow.eatda.domain.store.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun existsByName(name: String): Boolean
}
