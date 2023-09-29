package com.mjucow.eatda.domain.store.service.query

import com.mjucow.eatda.domain.store.service.query.dto.Categories
import com.mjucow.eatda.domain.store.service.query.dto.CategoryDto
import com.mjucow.eatda.persistence.store.CategoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CategoryQueryService(
    val repository: CategoryRepository,
) {
    fun findAll(): Categories {
        return Categories(repository.findAll().map(CategoryDto::from))
    }

    fun findById(id: Long): CategoryDto? {
        return repository.findByIdOrNull(id)?.run { CategoryDto.from(this) }
    }
}
