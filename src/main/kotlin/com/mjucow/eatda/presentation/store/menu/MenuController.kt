package com.mjucow.eatda.presentation.store.menu

import com.mjucow.eatda.domain.store.service.command.MenuCommandService
import com.mjucow.eatda.domain.store.service.command.dto.MenuUpdateCommand
import com.mjucow.eatda.domain.store.service.query.MenuDto
import com.mjucow.eatda.domain.store.service.query.MenuQueryService
import com.mjucow.eatda.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "메뉴 API", description = "메뉴를 관리해주는 API")
@RestController
@RequestMapping("/api/v1/menu")
class MenuController(
    private val queryService: MenuQueryService,
    private val commandService: MenuCommandService,
) {
    @Operation(summary = "메뉴 조회", description = "메뉴를 조회합니다.")
    @GetMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    fun findById(@PathVariable("menuId") id: Long): ApiResponse<MenuDto> {
        return ApiResponse.success(queryService.findById(id))
    }

    @Operation(summary = "메뉴 수정", description = "메뉴를 수정합니다.")
    @PatchMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateById(@PathVariable("menuId") id: Long, @RequestBody command: MenuUpdateCommand) {
        commandService.update(id, command)
    }

    @Operation(summary = "메뉴 삭제", description = "메뉴를 삭제합니다.")
    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable("menuId") id: Long) {
        commandService.delete(id)
    }
}
