package com.mjucow.eatda.domain.store.entity.objectmother

import com.mjucow.eatda.common.vo.DayOfWeek
import com.mjucow.eatda.domain.store.entity.StoreHours

object StoreHoursMother : EntityMother<StoreHours>() {
    override fun createDefaultInstance() = StoreHours(
        dayOfWeek = DayOfWeek.MON,
        openAt = StoreHours.MIN_TIME_MINUTE,
        closeAt = StoreHours.ONE_DAY_MINUTE
    )

    override fun createFillInstance() = StoreHours(
        dayOfWeek = DayOfWeek.MON,
        openAt = StoreHours.MIN_TIME_MINUTE,
        closeAt = StoreHours.MAX_TIME_MINUTE
    )
}
