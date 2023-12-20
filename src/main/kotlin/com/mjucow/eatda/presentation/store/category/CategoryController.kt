package com.mjucow.eatda.presentation.store.category

import com.mjucow.eatda.domain.store.service.command.CategoryCommandService
import com.mjucow.eatda.domain.store.service.command.dto.CreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.UpdateNameCommand
import com.mjucow.eatda.domain.store.service.query.CategoryQueryService
import com.mjucow.eatda.domain.store.service.query.dto.Categories
import com.mjucow.eatda.domain.store.service.query.dto.CategoryDto
import com.mjucow.eatda.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "카테고리 API")
@RestController
@RequestMapping("/api/v1/categories")
class CategoryController(
    val categoryQueryService: CategoryQueryService,
    val categoryCommandService: CategoryCommandService,
) {
    @Operation(summary = "카테고리 전체조회", description = "모든 카테고리를 조회합니다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): ApiResponse<Categories> {
        return ApiResponse.success(categoryQueryService.findAll())
    }

    @Operation(summary = "카테고리 생성", description = "카테고리를 생성합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody command: CreateCommand,
    ): ApiResponse<Long> {
        val id = categoryCommandService.create(command)

        return ApiResponse.success(id)
    }

    @Operation(summary = "카테고리 단건 조회", description = "카테고리 하나를 조회합니다.")
    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    fun findById(@PathVariable("categoryId") id: Long): ApiResponse<CategoryDto> {
        return ApiResponse.success(categoryQueryService.findById(id))
    }

    @Operation(summary = "카테고리 삭제", description = "카테고리를 삭제합니다.")
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable("categoryId") id: Long) {
        categoryCommandService.delete(id)
    }

    @Operation(summary = "카테고리 이름 수정", description = "카테고리의 이름을 수정합니다.")
    @PatchMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateNameById(
        @PathVariable("categoryId") id: Long,
        @RequestBody command: UpdateNameCommand,
    ) {
        categoryCommandService.updateName(id, command)
    }
}
