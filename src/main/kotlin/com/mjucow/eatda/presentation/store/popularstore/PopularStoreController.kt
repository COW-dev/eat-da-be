package com.mjucow.eatda.presentation.store.popularstore

import com.mjucow.eatda.domain.poplarstore.service.PopularStoreQueryService
import com.mjucow.eatda.domain.poplarstore.service.dto.PopularStoreDtos
import com.mjucow.eatda.presentation.common.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/stores/popular")
@RestController
class PopularStoreController(
    private val queryService: PopularStoreQueryService,
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAllPopularStore(): ApiResponse<PopularStoreDtos> {
        return ApiResponse.success(queryService.getPopularStores())
    }
}
