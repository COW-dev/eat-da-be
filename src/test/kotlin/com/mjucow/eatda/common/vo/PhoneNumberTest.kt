package com.mjucow.eatda.common.vo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class PhoneNumberTest : VOTest {

    @DisplayName("정확한 전화번호를 입력하면 객체가 생성된다")
    @ParameterizedTest
    @MethodSource("validNumbers")
    fun createInstanceWhenValidNumbers(number: String) {
        // given

        // when
        val sut = PhoneNumber(number)

        // then
        assertThat(sut).isNotNull
    }

    @DisplayName("정확한 전화번호를 입력하면 객체가 생성된다")
    @ParameterizedTest
    @MethodSource("invalidNumbers")
    fun throwExceptionWhenInvalidNumber(number: String) {
        // given

        // when
        val throwable = catchThrowable { PhoneNumber(number) }

        // then
        assertThat(throwable).isNotNull
    }
    companion object {
        @JvmStatic
        fun validNumbers(): List<String> {
            return listOf(
                "010-2885-9314",
                "031-330-6010",
                "02)123-4567",
                "02-123-4567"
            )
        }

        @JvmStatic
        fun invalidNumbers(): List<String> {
            return listOf(
                "01028859314",
                "02-13230-6010"
            )
        }
    }

    @Test
    override fun returnEqualsTrueAndSameHashcodeWhenSameValue() {
        // given
        val value = "010-2345-6789"
        val standardInstance = PhoneNumber(value)
        val sameValueInstance = PhoneNumber(value)

        // when
        val isEqaulsAndSameHashcode = (standardInstance == sameValueInstance) &&
            (sameValueInstance.hashCode() == sameValueInstance.hashCode())

        // then
        assertThat(isEqaulsAndSameHashcode).isTrue()
    }
}
