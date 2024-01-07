package com.mjucow.eatda.domain.store.service.query

import com.mjucow.eatda.domain.store.entity.Menu
import io.swagger.v3.oas.annotations.media.Schema

data class MenuDto(
    @Schema(name = "id", example = "25")
    val id: Long,
    @Schema(name = "name", example = "뿌링클")
    val name: String,
    @Schema(name = "price", example = "16000")
    val price: Int,
    @Schema(name = "imageAddress", example = "null")
    val imageAddress: String? = null,
) {
    companion object {
        fun from(entity: Menu) = MenuDto(
            id = entity.id,
            name = entity.name,
            price = entity.price,
            imageAddress = entity.imageAddress
        )
    }
}
