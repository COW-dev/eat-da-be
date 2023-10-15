package com.mjucow.eatda.domain.store.service.query

import com.mjucow.eatda.domain.store.service.query.dto.Categories
import com.mjucow.eatda.domain.store.service.query.dto.CategoryDto
import com.mjucow.eatda.persistence.store.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CategoryQueryService(
    val repository: CategoryRepository,
) {
    fun findAll(): Categories {
        return Categories(repository.findAll().map(CategoryDto::from))
    }

    fun findById(id: Long): CategoryDto {
        return CategoryDto.from(repository.getReferenceById(id))
    }
}
