package com.mjucow.eatda.domain.store.entity.objectmother

import com.mjucow.eatda.common.vo.PhoneNumber
import com.mjucow.eatda.domain.store.entity.Store
import org.springframework.data.geo.Point

object StoreMother : ObjectMother<Store>() {
    const val NAME = "명지대학교"
    const val ADDRESS = "서울특별시 서대문구 거북골로 34"
    const val DISPLAY_NAME = "띵지대"
    val PHONE_NUMBER = PhoneNumber("02-300-1656")
    val LOCATION = Point(126.9226047, 37.5802219)

    override val minimumInstance = Store(
        name = NAME,
        address = ADDRESS,
        displayName = null,
        phoneNumber = null,
        location = null
    )

    override val fillInstance = Store(
        name = NAME,
        address = ADDRESS,
        displayName = DISPLAY_NAME,
        phoneNumber = PHONE_NUMBER,
        location = LOCATION
    )
}
