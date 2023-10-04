package com.mjucow.eatda.domain.store.entity.objectmother

import com.mjucow.eatda.common.objectmother.EntityMother
import com.mjucow.eatda.common.vo.PhoneNumber
import com.mjucow.eatda.common.vo.Point
import com.mjucow.eatda.domain.store.entity.Store

object StoreMother : EntityMother<Store>() {
    const val NAME = "명지대학교"
    const val ADDRESS = "서울특별시 서대문구 거북골로 34"
    const val DISPLAY_NAME = "띵지대"
    const val IMAGE_ADDRESS = "/eatda/public/store/232D8241-C6A9-4AD9-B0EA-56F6DD24BADF.jpg"
    val PHONE_NUMBER = PhoneNumber("02-300-1656")
    val LOCATION = Point(latitude = 37.5802219, longitude = 126.9226047)

    override fun createDefaultInstance() = Store(NAME, ADDRESS)

    override fun createFillInstance() = Store(
        NAME,
        ADDRESS,
        DISPLAY_NAME,
        PHONE_NUMBER,
        IMAGE_ADDRESS,
        LOCATION
    )
}
