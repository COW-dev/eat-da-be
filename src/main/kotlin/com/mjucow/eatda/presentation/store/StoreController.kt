package com.mjucow.eatda.presentation.store

import com.mjucow.eatda.common.dto.CursorPage
import com.mjucow.eatda.domain.store.service.command.MenuCommandService
import com.mjucow.eatda.domain.store.service.command.StoreCommandService
import com.mjucow.eatda.domain.store.service.command.dto.MenuCreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.StoreCreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.StoreUpdateCommand
import com.mjucow.eatda.domain.store.service.query.MenuQueryService
import com.mjucow.eatda.domain.store.service.query.StoreQueryService
import com.mjucow.eatda.domain.store.service.query.dto.MenuList
import com.mjucow.eatda.domain.store.service.query.dto.StoreDetailDto
import com.mjucow.eatda.domain.store.service.query.dto.StoreDto
import com.mjucow.eatda.presentation.common.ApiResponse
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
import kotlin.math.min

@RestController
@RequestMapping("/api/v1/stores")
class StoreController(
    val storeQueryService: StoreQueryService,
    val storeCommandService: StoreCommandService,
    val menuQueryService: MenuQueryService,
    val menuCommandService: MenuCommandService,
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
        @RequestParam("storeId", required = false) storeId: Long?,
        @RequestParam("categoryId", required = false) categoryId: Long?,
        @RequestParam("size", required = false) pageSize: Int = 20,
    ): ApiResponse<CursorPage<StoreDto>> {
        val results = storeQueryService.findAllByCategoryAndCursor(storeId, categoryId, pageSize)

        val contents = results.subList(0, min(pageSize, results.size))
        val hasNext = results.size > pageSize
        val nextCursor = if (hasNext) contents.last().id.toString() else null
        return ApiResponse.success(CursorPage(contents, hasNext, nextCursor))
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

    @GetMapping("/{storeId}/menu")
    @ResponseStatus(HttpStatus.OK)
    fun findAllMenu(@PathVariable("storeId") id: Long): ApiResponse<MenuList> {
        return ApiResponse.success(menuQueryService.findAll(id))
    }

    @PostMapping("/{storeId}/menu")
    @ResponseStatus(HttpStatus.CREATED)
    fun createMenu(
        @PathVariable("storeId") id: Long,
        @RequestBody menuCreateCommand: MenuCreateCommand,
    ): ApiResponse<Long> {
        return ApiResponse.success(menuCommandService.create(id, menuCreateCommand))
    }
}
