package com.mjucow.eatda.common.vo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LatitudeTest : VOTest {

    @DisplayName("위도의 범위가 올바르면 객체가 생성된다.")
    @ParameterizedTest
    @ValueSource(doubles = [Latitude.LATITUDE_MIN, 34.0, 42.0, Latitude.LATITUDE_MAX])
    fun createInstanceWhenValidLatitude(value: Double) {
        // when
        val sut = Latitude(value)
        // then
        assertThat(sut).isNotNull
    }

    @DisplayName("위도의 범위가 올바르지않으면 에외가 발생한다.")
    @ParameterizedTest
    @ValueSource(doubles = [-Double.MAX_VALUE, Latitude.LATITUDE_MIN - 1, Latitude.LATITUDE_MAX + 1, Double.MAX_VALUE])
    fun throwExceptionWhenInvalidNumber(value: Double) {
        // when
        val throwable = catchThrowable { Latitude(value) }
        // then
        assertThat(throwable).isNotNull
    }

    @Test
    override fun returnEqualsTrueAndSameHashcodeWhenSameValue() {
        // given
        val value = 35.0
        val standardInstance = Latitude(value)
        val sameValueInstance = Latitude(value)
        // when
        val isEqaulsAndSameHashcode = (standardInstance == sameValueInstance) &&
            (sameValueInstance.hashCode() == sameValueInstance.hashCode())
        // then
        assertThat(isEqaulsAndSameHashcode).isTrue()
    }
}
