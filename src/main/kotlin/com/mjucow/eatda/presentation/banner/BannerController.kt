package com.mjucow.eatda.presentation.banner

import com.mjucow.eatda.domain.banner.service.command.BannerCommandService
import com.mjucow.eatda.domain.banner.service.command.dto.CreateBannerCommand
import com.mjucow.eatda.domain.banner.service.command.dto.UpdateBannerCommand
import com.mjucow.eatda.domain.banner.service.query.BannerQueryService
import com.mjucow.eatda.domain.banner.service.query.dto.Banners
import com.mjucow.eatda.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.jooq.Stringly.Param
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

@Tag(name = "배너 API", description = "배너 목록을 관리해주는 API")
@RequestMapping("/api/v1/banners")
@RestController
class BannerController(
    private val bannerQueryService: BannerQueryService,
    private val bannerCommandService: BannerCommandService,
) {
    @Operation(summary = "배너 생성", description = "배너를 생성합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody command: CreateBannerCommand,
    ): ApiResponse<Long> {
        return ApiResponse.success(bannerCommandService.create(command))
    }

    @Operation(summary = "배너 전체 조회", description = "모든 배너를 조회합니다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): ApiResponse<Banners> {
        return ApiResponse.success(bannerQueryService.findAll())
    }

    @Operation(summary = "배너 수정", description = "배너의 내용을 수정합니다.")
    @Parameter(name = "bannerId", description = "수정할 배너의 ID")
    @PatchMapping("/{bannerId}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @PathVariable("bannerId") bannerId: Long,
        @RequestBody command: UpdateBannerCommand,
    ) {
        return bannerCommandService.update(bannerId, command)
    }

    @Operation(summary = "배너 삭제", description = "배너를 삭제합니다.")
    @Parameter(name = "bannerId", description = "삭제할 배너의 ID")
    @DeleteMapping("/{bannerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable("bannerId") bannerId: Long,
    ) {
        return bannerCommandService.delete(bannerId)
    }
}
