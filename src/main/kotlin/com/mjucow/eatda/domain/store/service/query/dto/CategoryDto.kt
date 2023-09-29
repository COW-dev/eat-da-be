package com.mjucow.eatda.domain.store.service.query.dto

import com.mjucow.eatda.domain.store.entity.Category

data class CategoryDto(
    val id: Long,
    val name: String,
) {
    companion object {
        fun from(domain: Category): CategoryDto {
            return CategoryDto(domain.id, domain.name)
        }
    }
}
