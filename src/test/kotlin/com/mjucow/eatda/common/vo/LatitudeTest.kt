package com.mjucow.eatda.common.vo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
class LatitudeTest : VOTest {

    @DisplayName("위도의 범위가 올바르면 객체가 생성된다.")
    @ParameterizedTest
    @MethodSource("validValues")
    fun createInstanceWhenValidLatitude(value: Double) {
        // given

        // when
        val sut = Latitude(value)
        // then
        assertThat(sut).isNotNull
    }

    @DisplayName("위도의 범위가 올바르면 객체가 생성된다.")
    @ParameterizedTest
    @MethodSource("invalidValues")
    fun throwExceptionWhenInvalidNumber(value: Double) {
        // given

        // when
        val throwable = catchThrowable { Latitude(value) }
        // then
        assertThat(throwable).isNotNull
    }

    companion object {
        @JvmStatic
        fun validValues(): List<Double> {
            return listOf(
                40.42,
                10.0,
                -76.0,
                0.0
            )
        }

        @JvmStatic
        fun invalidValues(): List<Double> {
            return listOf(
                100.0,
                -100.0
            )
        }
    }

    @Test
    override fun returnEqualsTrueAndSameHashcodeWhenSameValue() {
        // given
        val value = 20.0
        val standardInstance = Latitude(value)
        val sameValueInstance = Latitude(value)
        // when
        val isEqaulsAndSameHashcode = (standardInstance == sameValueInstance) &&
            (sameValueInstance.hashCode() == sameValueInstance.hashCode())
        // then
        assertThat(isEqaulsAndSameHashcode).isTrue()
    }
}
