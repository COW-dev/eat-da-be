package com.mjucow.eatda.common.vo

enum class DayOfWeek(
    val korShortName: String,
    val korName: String,
    val engShortName: String,
    val engName: String,
) {
    MON("월", "월요일", "mon", "monday"),
    TUE("화", "화요일", "tue", "tuesday"),
    WED("수", "수요일", "wed", "wednesday"),
    THU("목", "목요일", "thu", "thursday"),
    FRI("금", "금요일", "fri", "friday"),
    SAT("토", "토요일", "sat", "saturday"),
    SUN("일", "일요일", "sun", "sunday"),
    ;

    /**
     * 객체가 인자의 다음 요일인지 확인합니다.
     */
    fun isNextDayOf(dayOfWeek: DayOfWeek): Boolean {
        return ((VALUES.size + ordinal - 1) % VALUES.size) == dayOfWeek.ordinal
    }

    /**
     * 객체가 인자의 전 요일인지 확인합니다
     */
    fun isPrevtDayOf(dayOfWeek: DayOfWeek): Boolean {
        return ((ordinal + 1) % VALUES.size) == dayOfWeek.ordinal
    }

    companion object {
        val VALUES = entries.toTypedArray()
    }
}
