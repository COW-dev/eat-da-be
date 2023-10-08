package com.mjucow.eatda.domain.store.service.query.dto

import com.mjucow.eatda.common.vo.DayOfWeek
import com.mjucow.eatda.domain.store.entity.StoreHours

data class StoreHoursDto(
    val dayOfWeek: DayOfWeek,
    val openAt: Int,
    val closeAt: Int,
) {
    companion object {
        fun from(entity: StoreHours) = StoreHoursDto(
            dayOfWeek = entity.dayOfWeek,
            openAt = entity.openAt,
            closeAt = entity.closeAt
        )
    }
}
