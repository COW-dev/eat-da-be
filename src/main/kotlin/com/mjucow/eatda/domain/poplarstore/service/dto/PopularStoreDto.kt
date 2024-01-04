package com.mjucow.eatda.domain.poplarstore.service.dto

import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.mjucow.eatda.common.vo.PhoneNumber
import com.mjucow.eatda.common.vo.Point
import com.mjucow.eatda.domain.poplarstore.entity.PopularStore
import com.mjucow.eatda.domain.store.entity.Store
import io.swagger.v3.oas.annotations.media.Schema

data class PopularStoreDto(
    @Schema(name = "id", example = "3")
    val id: Long,
    @Schema(name = "displayedName", example = "명지대학교")
    val displayedName: String,
    @Schema(name = "address", example = "서울특별시 서대문구 거북골로 34")
    val address: String,
    @JsonUnwrapped
    val phoneNumber: PhoneNumber? = null,
    @Schema(name = "imageAddress", example = "/eatda/public/store/232D8241-C6A9-4AD9-B0EA-56F6DD24BADF.jpg")
    val imageAddress: String? = null,
    val location: Point? = null,
    @Schema(name = "count", example = "1")
    val count: Long,
) {
    companion object {
        fun of(popularEntity: PopularStore, entity: Store): PopularStoreDto {
            if (popularEntity.storeId != entity.id) {
                throw IllegalArgumentException()
            }

            return PopularStoreDto(
                id = entity.id,
                displayedName = entity.displayedName,
                address = entity.address,
                phoneNumber = entity.phoneNumber,
                imageAddress = entity.imageAddress,
                location = entity.location,
                count = popularEntity.count
            )
        }
    }
}
