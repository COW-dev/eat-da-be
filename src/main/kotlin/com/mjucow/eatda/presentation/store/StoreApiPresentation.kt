package com.mjucow.eatda.presentation.store

import com.mjucow.eatda.common.dto.CursorPage
import com.mjucow.eatda.domain.store.service.command.dto.MenuCreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.StoreCreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.StoreUpdateCommand
import com.mjucow.eatda.domain.store.service.query.dto.MenuList
import com.mjucow.eatda.domain.store.service.query.dto.StoreDetailDto
import com.mjucow.eatda.domain.store.service.query.dto.StoreDto
import com.mjucow.eatda.presentation.common.ApiResponse
import com.mjucow.eatda.presentation.common.example.StoreDtosApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "가게 API", description = "가게를 관리해주는 API")
interface StoreApiPresentation {

    @Operation(summary = "가게 생성", description = "가게를 생성합니다.")
    fun create(command: StoreCreateCommand): ApiResponse<Long>

    @Operation(summary = "커서 기반 카테고리 가게 조회", description = "커서를 기반으로 가게를 조회합니다.")
    @StoreDtosApiResponse
    fun findAllByCategoryIdAndCursor(
        storeId: Long?,
        categoryId: Long?,
        pageSize: Int,
    ): ApiResponse<CursorPage<StoreDto>>

    @Operation(summary = "특정 가게 조회", description = "특정 가게 하나를 조회합니다.")
    fun findById(id: Long): ApiResponse<StoreDetailDto>

    @Operation(summary = "특정 가게 정보 수정", description = "특정 가게의 정보를 수정합니다.")
    fun updateById(id: Long, command: StoreUpdateCommand)

    @Operation(summary = "특정 가게 정보 삭제", description = "특정 가게의 정보를 삭제합니다.")
    fun deleteById(id: Long)

    @Operation(summary = "가게 메뉴 전체 조회", description = "한 가게의 전체 메뉴를 조회합니다.")
    fun findAllMenu(id: Long): ApiResponse<MenuList>

    @Operation(summary = "가게 메뉴 생성", description = "가게의 메뉴를 생성합니다.")
    fun createMenu(id: Long, menuCreateCommand: MenuCreateCommand): ApiResponse<Long>
}
