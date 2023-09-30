package com.mjucow.eatda.common.vo

import org.assertj.core.api.Assertions.assertThat

abstract class VOTest<T : Any> {
    abstract val voTestStandardInstance: T
    abstract val voTestSameValueInstance: T

    fun returnEqualsTrueAndSameHashcodeWhenSameValue() {
        // given

        // when
        val equals = voTestStandardInstance == voTestSameValueInstance
        val hashcodeEq = voTestStandardInstance.hashCode() == voTestSameValueInstance.hashCode()

        // then
        assertThat(equals && hashcodeEq).isTrue()
    }
}
