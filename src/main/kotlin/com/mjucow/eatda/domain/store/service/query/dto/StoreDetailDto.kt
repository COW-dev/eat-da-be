package com.mjucow.eatda.domain.store.service.query.dto

import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.mjucow.eatda.common.vo.PhoneNumber
import com.mjucow.eatda.common.vo.Point
import com.mjucow.eatda.domain.store.entity.Store
import io.swagger.v3.oas.annotations.media.Schema

data class StoreDetailDto(
    @Schema(name = "id", example = "5")
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
    @JsonUnwrapped val categories: Categories? = null,
    @JsonUnwrapped val storeHours: StoreHoursList? = null,
) {
    companion object {
        fun from(entity: Store) = StoreDetailDto(
            id = entity.id,
            displayedName = entity.displayedName,
            address = entity.address,
            phoneNumber = entity.phoneNumber,
            imageAddress = entity.imageAddress,
            location = entity.location,
            categories = Categories(entity.getCategories().map(CategoryDto::from)),
            storeHours = StoreHoursList(entity.getStoreHours().map(StoreHoursDto::from))
        )
    }
}
