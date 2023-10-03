package com.mjucow.eatda.domain.store.service.query.dto

import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.mjucow.eatda.common.vo.PhoneNumber
import com.mjucow.eatda.common.vo.Point
import com.mjucow.eatda.domain.store.entity.Store

data class StoreDetailDto(
    val id: Long,
    val displayedName: String,
    val address: String,
    @JsonUnwrapped val phoneNumber: PhoneNumber? = null,
    val imageAddress: String? = null,
    val location: Point? = null,
    val categories: Categories? = null,
    val storeHours: StoreHoursList? = null,
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
