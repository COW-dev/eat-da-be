package com.mjucow.eatda.domain.store.entity

import autoparams.kotlin.AutoKotlinSource
import com.mjucow.eatda.common.vo.DayOfWeek
import com.mjucow.eatda.domain.store.entity.objectmother.StoreHoursMother
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import javax.validation.constraints.Max
import javax.validation.constraints.Min

class StoreHoursTest {
    @DisplayName("시작 시간이 음수면 예외를 던진다")
    @ParameterizedTest
    @AutoKotlinSource
    fun throwExceptionWhenNegativeOpenAt(
        @Max(-1)
        openAt: Int,
    ) {
        // given
        val closeAt = StoreHours.MAX_TIME_MINUTE

        // when
        val createThrowable = catchThrowable { StoreHours(DayOfWeek.MON, openAt, closeAt) }
        val setterThrowable = catchThrowable { StoreHoursMother.create { it.openAt = openAt } }

        // then
        assertThat(createThrowable).isNotNull()
        assertThat(setterThrowable).isNotNull()
    }

    @DisplayName("시작 시간이 24시간을 넘어가면 예외를 던진다")
    @ParameterizedTest
    @AutoKotlinSource
    fun throwExceptionWhenOpenAtOverOneDay(
        @Min(StoreHours.ONE_DAY_MINUTE.toLong())
        @Max(StoreHours.MAX_TIME_MINUTE.toLong())
        openAt: Int,
    ) {
        // given
        val closeAt = StoreHours.MAX_TIME_MINUTE

        // when
        val createThrowable = catchThrowable { StoreHours(DayOfWeek.MON, openAt, closeAt) }
        val setterThrowable = catchThrowable { StoreHoursMother.create { it.openAt = openAt } }

        // then
        assertThat(createThrowable).isNotNull()
        assertThat(setterThrowable).isNotNull()
    }

    @DisplayName("종료 시간이 음수면 예외를 던진다")
    @ParameterizedTest
    @AutoKotlinSource
    fun throwExceptionWhenNegativeCloseAt(
        @Max(-1)
        closeAt: Int,
    ) {
        // given
        val openAt = StoreHours.MIN_TIME_MINUTE

        // when
        val createThrowable = catchThrowable { StoreHours(DayOfWeek.MON, openAt, closeAt) }
        val setterThrowable = catchThrowable { StoreHoursMother.create { it.closeAt = closeAt } }

        // then
        assertThat(createThrowable).isNotNull()
        assertThat(setterThrowable).isNotNull()
    }

    @DisplayName("종료 시간이 24시간을 넘어가면 예외를 던진다")
    @ParameterizedTest
    @AutoKotlinSource
    fun throwExceptionWhenCloseAtOverMaxValue(
        @Min((StoreHours.MAX_TIME_MINUTE + 1).toLong())
        closeAt: Int,
    ) {
        // given
        val openAt = StoreHours.MIN_TIME_MINUTE

        // when
        val createThrowable = catchThrowable { StoreHours(DayOfWeek.MON, openAt, closeAt) }
        val setterThrowable = catchThrowable { StoreHoursMother.create { it.closeAt = closeAt } }

        // then
        assertThat(createThrowable).isNotNull()
        assertThat(setterThrowable).isNotNull()
    }

    @DisplayName("시작 시간이 종료 시간보다 크거나 같으면 예외를 던진다")
    @ParameterizedTest
    @AutoKotlinSource
    fun throwExceptionWhenOpenAtGreatThanCloseAt(
        @Min(StoreHours.MIN_TIME_MINUTE.toLong())
        @Max(StoreHours.ONE_DAY_MINUTE.toLong())
        openAt: Int,
    ) {
        // given
        val closeAt = StoreHours.MIN_TIME_MINUTE

        // when
        val createThrowable = catchThrowable { StoreHours(DayOfWeek.MON, openAt, closeAt) }
        val openAtSetterThrowable = catchThrowable { StoreHoursMother.create { it.openAt = it.closeAt } }
        val closeAtSetterThrowable = catchThrowable { StoreHoursMother.create { it.closeAt = it.openAt } }

        // then
        assertThat(createThrowable).isNotNull()
        assertThat(openAtSetterThrowable).isNotNull()
        assertThat(closeAtSetterThrowable).isNotNull()
    }

    @DisplayName("정상적인 값이 들어온다면 객체가 생성된다")
    @ParameterizedTest
    @EnumSource
    fun throwExceptionWhenOpenAtGreatThanCloseAt(dayOfWeek: DayOfWeek) {
        // given
        val openAt = StoreHours.MIN_TIME_MINUTE
        val closeAt = StoreHours.MAX_TIME_MINUTE

        // when
        val instance = StoreHours(dayOfWeek, openAt, closeAt)

        // then
        assertThat(instance).isNotNull()
    }
}
