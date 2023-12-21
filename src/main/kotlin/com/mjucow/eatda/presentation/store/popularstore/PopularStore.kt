package com.mjucow.eatda.presentation.store.popularstore

import com.mjucow.eatda.domain.poplarstore.service.dto.PopularStoreDtos
import com.mjucow.eatda.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "인기 매장 API", description = "인기 매장을 관리해주는 API")
interface PopularStore {
    @Operation(summary = "인기 매장 전체조회", description = "모든 인기 매장을 조회합니다.")
    fun findAllPopularStore(): ApiResponse<PopularStoreDtos>
}
