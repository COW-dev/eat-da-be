package com.mjucow.eatda.presentation.banner

import com.mjucow.eatda.domain.banner.service.command.dto.CreateBannerCommand
import com.mjucow.eatda.domain.banner.service.command.dto.UpdateBannerCommand
import com.mjucow.eatda.domain.banner.service.query.dto.Banners
import com.mjucow.eatda.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "배너 API", description = "배너 목록을 관리해주는 API")
interface Banner {

    @Operation(summary = "배너 생성", description = "배너를 생성합니다.")
    fun create(command: CreateBannerCommand): ApiResponse<Long>

    @Operation(summary = "배너 전체 조회", description = "모든 배너를 조회합니다.")
    fun findAll(): ApiResponse<Banners>

    @Operation(summary = "배너 수정", description = "배너의 내용을 수정합니다.")
    @Parameter(name = "bannerId", description = "수정할 배너의 ID")
    fun update(bannerId: Long, command: UpdateBannerCommand)

    @Operation(summary = "배너 삭제", description = "배너를 삭제합니다.")
    @Parameter(name = "bannerId", description = "삭제할 배너의 ID")
    fun delete(bannerId: Long)
}
