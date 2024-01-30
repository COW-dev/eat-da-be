package com.mjucow.eatda.domain.curation.service.query.dto

import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.mjucow.eatda.domain.curation.entity.Curation
import com.mjucow.eatda.domain.store.service.query.dto.StoreDto
import com.mjucow.eatda.domain.store.service.query.dto.Stores
import io.swagger.v3.oas.annotations.media.Schema

data class CurationDto(
    @Schema(name = "id", example = "1")
    val id: Long,
    @Schema(name = "title", example = "명지대 점심 특선")
    val title: String,
    @Schema(name = "description", example = "점심 특선 메뉴를 판매하는 음식점들이에요.")
    val description: String,
    @JsonUnwrapped val stores: Stores? = null,
) {

    companion object {
        fun from(domain: Curation): CurationDto {
            return CurationDto(
                id = domain.id,
                title = domain.title,
                description = domain.description,
                stores = Stores(domain.getStores().map(StoreDto::from))
            )
        }
    }
}
