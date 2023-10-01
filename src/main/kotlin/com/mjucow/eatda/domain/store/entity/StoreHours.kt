package com.mjucow.eatda.domain.store.entity

import com.mjucow.eatda.common.vo.DayOfWeek
import com.mjucow.eatda.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.Table


@Entity
@Table(name = "store_hours")
class StoreHours(
    @Enumerated
    @Column(nullable = false, updatable = false)
    val dayOfWeek: DayOfWeek = DayOfWeek.MON
) : BaseEntity() {
    constructor(dayOfWeek: DayOfWeek, openAt: Int, closeAt: Int) : this(dayOfWeek) {
        this.openAt = openAt
        validateOpenAt()
        this.closeAt = closeAt
        validateCloseAt()
    }

    @Column(nullable = false)
    var openAt: Int = MIN_TIME_MINUTE
        set(value) {
            field = value
            validateOpenAt()
        }

    @Column(nullable = false)
    var closeAt: Int = MAX_TIME_MINUTE
        set(value) {
            field = value
            validateCloseAt()
        }

    private fun validateOpenAt() {
        require(openAt in MIN_TIME_MINUTE..<ONE_DAY_MINUTE && openAt < closeAt)
    }

    private fun validateCloseAt() {
        require(closeAt in MIN_TIME_MINUTE..MAX_TIME_MINUTE && openAt < closeAt)
    }

    companion object {
        // 가게의 새벽 영업을 표현을 위해 시작, 종료 시간은 분으로 변환하여 표기한다.
        const val MIN_TIME_MINUTE = 0
        const val ONE_DAY_MINUTE = 24 * 60
        const val MAX_TIME_MINUTE = 60 * 36
    }
}
