package com.mjucow.eatda.presentation.store.popularstore

import com.mjucow.eatda.domain.poplarstore.service.PopularStoreQueryService
import com.mjucow.eatda.domain.poplarstore.service.dto.PopularStoreDtos
import com.mjucow.eatda.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인기 매장 API", description = "인기 매장을 관리해주는 API")
@RequestMapping("/api/v1/stores/popular")
@RestController
class PopularStoreController(
    private val queryService: PopularStoreQueryService,
) {
    @Operation(summary = "인기 매장 전체조회", description = "모든 인기 매장을 조회합니다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAllPopularStore(): ApiResponse<PopularStoreDtos> {
        return ApiResponse.success(queryService.getPopularStores())
    }
}
