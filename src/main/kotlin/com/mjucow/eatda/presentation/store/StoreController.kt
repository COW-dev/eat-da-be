package com.mjucow.eatda.presentation.store

import com.mjucow.eatda.domain.store.service.command.StoreCommandService
import com.mjucow.eatda.domain.store.service.command.dto.StoreCreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.StoreUpdateCommand
import com.mjucow.eatda.domain.store.service.query.StoreQueryService
import com.mjucow.eatda.domain.store.service.query.dto.StoreDetailDto
import com.mjucow.eatda.domain.store.service.query.dto.StoreDto
import com.mjucow.eatda.presentation.common.ApiResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/stores")
class StoreController(
    val storeQueryService: StoreQueryService,
    val storeCommandService: StoreCommandService,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody command: StoreCreateCommand): ApiResponse<Long> {
        val id = storeCommandService.create(command = command)
        return ApiResponse.success(id)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAllByCategoryIdAndCursor(
        @RequestParam("storeId", required = false) id: Long?,
        @RequestParam("categoryId", required = false) categoryId: Long?,
        @PageableDefault(size = 20) page: Pageable,
    ): ApiResponse<Slice<StoreDto>> {
        val storeDtos = storeQueryService.findAllByCategoryAndCursor(id, categoryId, page)
        return ApiResponse.success(storeDtos)
    }

    @GetMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    fun findById(@PathVariable("storeId") id: Long): ApiResponse<StoreDetailDto> {
        val dto = storeQueryService.findById(id)
        return ApiResponse.success(dto)
    }

    @PatchMapping("/{storeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateById(@PathVariable("storeId") id: Long, @RequestBody command: StoreUpdateCommand) {
        storeCommandService.update(id, command)
    }

    @DeleteMapping("/{storeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable("storeId") id: Long) {
        storeCommandService.delete(id)
    }
}
