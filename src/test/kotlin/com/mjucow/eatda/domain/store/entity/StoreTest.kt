package com.mjucow.eatda.domain.store.entity

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource

class StoreTest {
    @DisplayName("이름이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun throwExceptionWhenNameIsEmpty(name: String) {
        // given

        // when
        val throwable = Assertions.catchThrowable { Store(name) }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }


    @DisplayName("이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNameLengthGreaterThanMaxLength() {
        // given
        val name = "x".repeat(Store.MAX_NAME_LENGTH + 1)

        // when
        val throwable = Assertions.catchThrowable { Store(name) }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }


    @DisplayName("보이는 이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenDisplayNameLengthGreaterThanMaxLength() {
        // given
        val name = "validName"
        val displayName = "x".repeat(Store.MAX_NAME_LENGTH + 1)

        // when
        val throwable = Assertions.catchThrowable { Store(name, displayName) }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }


    @DisplayName("새로운 이름이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun throwExceptionWhenNewNameIsEmpty(newName: String) {
        // given
        val store = Store("validName")

        // when
        val throwable = Assertions.catchThrowable { store.name = newName }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNewNameLengthGreaterThanMaxLength() {
        // given
        val store = Store("validName")
        val newName = "x".repeat(Store.MAX_NAME_LENGTH + 1)

        // when
        val throwable = Assertions.catchThrowable { store.name = newName }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 보이는 이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNewDisplayNameLengthGreaterThanMaxLength() {
        // given
        val store = Store("validName", "validDisplayName")
        val newDisplayName = "x".repeat(Store.MAX_NAME_LENGTH + 1)

        // when
        val throwable = Assertions.catchThrowable { store.displayName = newDisplayName }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("보이는 이름이 없다면 이름이 보여진다")
    @Test
    fun returnNameWhenDisplayNameIsNull() {
        // given
        val name = "validName"
        val store = Store(name)

        // when
        val displayedName = store.getDisplayedName()

        // then
        Assertions.assertThat(displayedName).isEqualTo(name)
    }

    @DisplayName("보이는 이름이 있다면 보이는 이름이 보여진다")
    @Test
    fun returnDisplayNameWhenDisplayNameIsNotNull() {
        // given
        val name = "validName"
        val displayName = "validDisplayName"
        val store = Store(name, displayName)

        // when
        val displayedName = store.getDisplayedName()

        // then
        Assertions.assertThat(displayedName).isEqualTo(displayName)
    }

    @DisplayName("정상적인 경우 객체가 생성된다")
    @Test
    fun createInstantWhenValidInput() {
        // given
        val name = "validName"

        // when
        val category = Store(name)

        // then
        Assertions.assertThat(category).isNotNull
    }
}
