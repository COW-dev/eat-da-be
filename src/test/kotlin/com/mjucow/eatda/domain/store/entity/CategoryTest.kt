package com.mjucow.eatda.domain.store.entity

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource

class CategoryTest {
    @DisplayName("이름이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun throwExceptionWhenNameIsEmpty(name: String) {
        // given

        // when
        val throwable = catchThrowable { Category(name) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }


    @DisplayName("이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNameLengthGreaterThanMaxLength() {
        // given
        val name = "x".repeat(Category.MAX_NAME_LENGTH + 1)

        // when
        val throwable = catchThrowable { Category(name) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }


    @DisplayName("새로운 이름이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun throwExceptionWhenNewNameIsEmpty(newName: String) {
        // given
        val category = Category("validName")

        // when
        val throwable = catchThrowable { category.name = newName }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNewNameLengthGreaterThanMaxLength() {
        // given
        val category = Category("validName")
        val newName = "x".repeat(Category.MAX_NAME_LENGTH + 1)

        // when
        val throwable = catchThrowable { category.name = newName }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("정상적인 경우 객체가 생성된다")
    @Test
    fun createInstantWhenValidInput() {
        // given
        val name = "validName"

        // when
        val category = Category(name)

        // then
        assertThat(category).isNotNull
    }
}
