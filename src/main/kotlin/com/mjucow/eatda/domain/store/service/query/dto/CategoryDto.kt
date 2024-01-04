package com.mjucow.eatda.domain.store.service.query.dto

import com.mjucow.eatda.domain.store.entity.Category
import io.swagger.v3.oas.annotations.media.Schema

data class CategoryDto(
    @Schema(name = "id", description = "카테고리 식별자", example = "10")
    val id: Long,
    @Schema(name = "name", description = "카테고리 이름은 String 타입이어야 합니다.", example = "치킨")
    val name: String,
) {
    companion object {
        fun from(domain: Category): CategoryDto {
            return CategoryDto(domain.id, domain.name)
        }
    }
}
