package com.mjucow.eatda.domain.store.entity.objectmother

import com.mjucow.eatda.common.vo.DayOfWeek
import com.mjucow.eatda.domain.store.entity.StoreHours

object StoreHoursMother : EntityMother<StoreHours>() {
    override fun createDefaultInstance() = StoreHours(
        DayOfWeek.MON,
        StoreHours.MIN_TIME_MINUTE,
        StoreHours.ONE_DAY_MINUTE
    )

    override fun createFillInstance() = StoreHours(
        DayOfWeek.MON,
        StoreHours.MIN_TIME_MINUTE,
        StoreHours.MAX_TIME_MINUTE
    )
}
