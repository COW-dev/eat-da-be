package com.mjucow.eatda.presentation.store.menu

import com.mjucow.eatda.domain.store.service.command.MenuCommandService
import com.mjucow.eatda.domain.store.service.command.dto.MenuUpdateCommand
import com.mjucow.eatda.domain.store.service.query.MenuDto
import com.mjucow.eatda.domain.store.service.query.MenuQueryService
import com.mjucow.eatda.presentation.common.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/menu")
class MenuController(
    private val queryService: MenuQueryService,
    private val commandService: MenuCommandService,
) : Menu {
    @GetMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    override fun findById(@PathVariable("menuId") id: Long): ApiResponse<MenuDto> {
        return ApiResponse.success(queryService.findById(id))
    }

    @PatchMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    override fun updateById(@PathVariable("menuId") id: Long, @RequestBody command: MenuUpdateCommand) {
        commandService.update(id, command)
    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun deleteById(@PathVariable("menuId") id: Long) {
        commandService.delete(id)
    }
}
