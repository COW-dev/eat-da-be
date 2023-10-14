package com.mjucow.eatda.domain.store.entity.objectmother

import com.mjucow.eatda.common.objectmother.EntityMother
import com.mjucow.eatda.domain.store.entity.Menu

object MenuMother : EntityMother<Menu>() {
    const val NAME = "menu"
    const val PRICE = 1000
    const val IMAGE_ADDRESS = "/eatda/public/menu/232D8241-C6A9-4AD9-B0EA-56F6DD24BADF.jpg"
    var store = StoreMother.create()

    override fun createFillInstance() = Menu(
        name = NAME,
        price = PRICE,
        imageAddress = IMAGE_ADDRESS,
        store = store
    )

    override fun createDefaultInstance() = Menu(
        name = NAME,
        price = PRICE,
        store = store
    )
}
