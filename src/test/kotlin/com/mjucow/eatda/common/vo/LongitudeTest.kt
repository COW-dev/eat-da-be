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
    @ValueSource(doubles = [0.0, 1.0, 179.0, 180.0])
    fun createInstanceWhenValidLatitude(value: Double) {
        // given

        // when
        val sut = Longitude(value)
        // then
        assertThat(sut).isNotNull
    }

    @DisplayName("위도의 범위가 올바르면 객체가 생성된다.")
    @ParameterizedTest
    @ValueSource(doubles = [-Double.MAX_VALUE, -1.0, 181.0, Double.MAX_VALUE])
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
