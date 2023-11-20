package com.mjucow.eatda.domain.poplarstore.service.dto

import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.mjucow.eatda.common.vo.PhoneNumber
import com.mjucow.eatda.common.vo.Point
import com.mjucow.eatda.domain.poplarstore.entity.PopularStore
import com.mjucow.eatda.domain.store.entity.Store

data class PopularStoreDto(
    val id: Long,
    val displayedName: String,
    val address: String,
    @JsonUnwrapped val phoneNumber: PhoneNumber? = null,
    val imageAddress: String? = null,
    val location: Point? = null,
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
