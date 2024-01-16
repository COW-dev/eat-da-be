package com.mjucow.eatda.common.vo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LongitudeTest : VOTest {

    @DisplayName("경도의 범위가 올바르면 객체가 생성된다.")
    @ParameterizedTest
    @ValueSource(doubles = [Longitude.LONGITUDE_MIN, 125.0, 131.0, Longitude.LONGITUDE_MAX])
    fun createInstanceWhenValidLatitude(value: Double) {
        // given

        // when
        val sut = Longitude(value)
        // then
        assertThat(sut).isNotNull
    }

    @DisplayName("위도의 범위가 올바르지 않으면 에외처리가 된다.")
    @ParameterizedTest
    @ValueSource(doubles = [-Double.MAX_VALUE, Longitude.LONGITUDE_MIN - 1, Longitude.LONGITUDE_MAX + 1, Double.MAX_VALUE])
    fun throwExceptionWhenInvalidNumber(value: Double) {
        // given

        // when
        val throwable = catchThrowable { Longitude(value) }
        // then
        assertThat(throwable).isNotNull
    }

    @Test
    override fun returnEqualsTrueAndSameHashcodeWhenSameValue() {
        // given
        val value = 20.0
        val standardInstance = Longitude(value)
        val sameValueInstance = Longitude(value)
        // when
        val isEqaulsAndSameHashcode = (standardInstance == sameValueInstance) &&
            (sameValueInstance.hashCode() == sameValueInstance.hashCode())
        // then
        assertThat(isEqaulsAndSameHashcode).isTrue()
    }
}
