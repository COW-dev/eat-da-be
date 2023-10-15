package com.mjucow.eatda.presentation.store.category

import com.mjucow.eatda.domain.store.service.command.CategoryCommandService
import com.mjucow.eatda.domain.store.service.command.dto.CreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.UpdateNameCommand
import com.mjucow.eatda.domain.store.service.query.CategoryQueryService
import com.mjucow.eatda.domain.store.service.query.dto.Categories
import com.mjucow.eatda.domain.store.service.query.dto.CategoryDto
import com.mjucow.eatda.presentation.common.ApiResponse
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

@RestController
@RequestMapping("/api/v1/categories")
class CategoryController(
    val categoryQueryService: CategoryQueryService,
    val categoryCommandService: CategoryCommandService,
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): ApiResponse<Categories> {
        return ApiResponse.success(categoryQueryService.findAll())
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody command: CreateCommand): ApiResponse<Long> {
        val id = categoryCommandService.create(command)

        return ApiResponse.success(id)
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    fun findById(@PathVariable("categoryId") id: Long): ApiResponse<CategoryDto> {
        return ApiResponse.success(categoryQueryService.findById(id))
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable("categoryId") id: Long) {
        categoryCommandService.delete(id)
    }

    @PatchMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateNameById(
        @PathVariable("categoryId") id: Long,
        @RequestBody command: UpdateNameCommand,
    ) {
        categoryCommandService.updateName(id, command)
    }
}
