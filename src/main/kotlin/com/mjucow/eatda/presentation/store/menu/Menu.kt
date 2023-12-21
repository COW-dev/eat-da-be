package com.mjucow.eatda.presentation.store.menu

import com.mjucow.eatda.domain.store.service.command.dto.MenuUpdateCommand
import com.mjucow.eatda.domain.store.service.query.MenuDto
import com.mjucow.eatda.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "메뉴 API", description = "메뉴를 관리해주는 API")
interface Menu {
    @Operation(summary = "메뉴 조회", description = "메뉴를 조회합니다.")
    fun findById(id: Long): ApiResponse<MenuDto>

    @Operation(summary = "메뉴 수정", description = "메뉴를 수정합니다.")
    fun updateById(id: Long, command: MenuUpdateCommand)

    @Operation(summary = "메뉴 삭제", description = "메뉴를 삭제합니다.")
    fun deleteById(id: Long)
}
