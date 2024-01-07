package com.mjucow.eatda.presentation.banner

import com.mjucow.eatda.domain.banner.service.command.BannerCommandService
import com.mjucow.eatda.domain.banner.service.command.dto.CreateBannerCommand
import com.mjucow.eatda.domain.banner.service.command.dto.UpdateBannerCommand
import com.mjucow.eatda.domain.banner.service.query.BannerQueryService
import com.mjucow.eatda.domain.banner.service.query.dto.Banners
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

@RequestMapping("/api/v1/banners")
@RestController
class BannerController(
    private val bannerQueryService: BannerQueryService,
    private val bannerCommandService: BannerCommandService,
) : BannerApiPresentation {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    override fun create(
        @RequestBody command: CreateBannerCommand,
    ): ApiResponse<Long> {
        return ApiResponse.success(bannerCommandService.create(command))
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    override fun findAll(): ApiResponse<Banners> {
        return ApiResponse.success(bannerQueryService.findAll())
    }

    @PatchMapping("/{bannerId}")
    @ResponseStatus(HttpStatus.OK)
    override fun update(
        @PathVariable("bannerId") bannerId: Long,
        @RequestBody command: UpdateBannerCommand,
    ) {
        return bannerCommandService.update(bannerId, command)
    }

    @DeleteMapping("/{bannerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun delete(
        @PathVariable("bannerId") bannerId: Long,
    ) {
        return bannerCommandService.delete(bannerId)
    }
}
