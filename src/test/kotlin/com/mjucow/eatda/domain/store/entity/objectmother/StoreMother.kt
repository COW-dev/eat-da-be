package com.mjucow.eatda.domain.store.entity.objectmother

import com.mjucow.eatda.common.vo.PhoneNumber
import com.mjucow.eatda.common.vo.Point
import com.mjucow.eatda.domain.store.entity.Store

object StoreMother : EntityMother<Store>() {
    const val NAME = "명지대학교"
    const val ADDRESS = "서울특별시 서대문구 거북골로 34"
    const val DISPLAY_NAME = "띵지대"
    val PHONE_NUMBER = PhoneNumber("02-300-1656")
    val LOCATION = Point(latitude = 37.5802219, longitude = 126.9226047)

    override fun createDefaultInstance() = Store(
        name = NAME,
        address = ADDRESS,
        displayName = null,
        phoneNumber = null,
        location = null
    )

    override fun createFillInstance() = Store(
        name = NAME,
        address = ADDRESS,
        displayName = DISPLAY_NAME,
        phoneNumber = PHONE_NUMBER,
        location = LOCATION
    )
}
