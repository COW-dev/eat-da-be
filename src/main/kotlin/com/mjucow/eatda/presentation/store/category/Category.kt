package com.mjucow.eatda.presentation.store.category

import com.mjucow.eatda.domain.store.service.command.dto.CreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.UpdateNameCommand
import com.mjucow.eatda.domain.store.service.query.dto.Categories
import com.mjucow.eatda.domain.store.service.query.dto.CategoryDto
import com.mjucow.eatda.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "카테고리 API", description = "카테고리를 관리하는 API")
interface Category {
    @Operation(summary = "카테고리 전체조회", description = "모든 카테고리를 조회합니다.")
    fun findAll(): ApiResponse<Categories>

    @Operation(summary = "카테고리 생성", description = "카테고리를 생성합니다.")
    fun create(command: CreateCommand): ApiResponse<Long>

    @Operation(summary = "카테고리 단건 조회", description = "카테고리 하나를 조회합니다.")
    fun findById(id: Long): ApiResponse<CategoryDto>

    @Operation(summary = "카테고리 삭제", description = "카테고리를 삭제합니다.")
    fun deleteById(id: Long)

    @Operation(summary = "카테고리 이름 수정", description = "카테고리의 이름을 수정합니다.")
    fun updateNameById(id: Long, command: UpdateNameCommand)
}
