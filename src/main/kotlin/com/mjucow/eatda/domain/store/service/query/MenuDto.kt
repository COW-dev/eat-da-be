package com.mjucow.eatda.domain.store.service.query

import com.mjucow.eatda.domain.store.entity.Menu

data class MenuDto(
    val id: Long,
    val name: String,
    val price: Int,
    val imageAddress: String? = null
) {
    companion object {
        fun from(entity: Menu) =  MenuDto(
            id = entity.id,
            name = entity.name,
            price = entity.price,
            imageAddress = entity.imageAddress,
        )
    }
}
