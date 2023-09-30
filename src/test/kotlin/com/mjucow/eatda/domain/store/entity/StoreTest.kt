package com.mjucow.eatda.domain.store.entity

import com.mjucow.eatda.domain.store.entity.objectmother.StoreMother
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
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
        val throwable = catchThrowable { Store(name = name, address = StoreMother.ADDRESS) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNameLengthGreaterThanMaxLength() {
        // given
        val name = "x".repeat(Store.MAX_NAME_LENGTH + 1)

        // when
        val throwable = catchThrowable { Store(name = name, address = StoreMother.ADDRESS) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("보이는 이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenDisplayNameLengthGreaterThanMaxLength() {
        // given
        val name = "validName"
        val displayName = "x".repeat(Store.MAX_NAME_LENGTH + 1)

        // when
        val throwable = catchThrowable {
            Store(name = name, address = StoreMother.ADDRESS, displayName = displayName)
        }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 이름이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun throwExceptionWhenNewNameIsEmpty(newName: String) {
        // given
        val store = StoreMother.get()

        // when
        val throwable = catchThrowable { store.name = newName }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNewNameLengthGreaterThanMaxLength() {
        // given
        val store = StoreMother.get()
        val newName = "x".repeat(Store.MAX_NAME_LENGTH + 1)

        // when
        val throwable = catchThrowable { store.name = newName }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 보이는 이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNewDisplayNameLengthGreaterThanMaxLength() {
        // given
        val store = StoreMother.get()
        val newDisplayName = "x".repeat(Store.MAX_NAME_LENGTH + 1)

        // when
        val throwable = catchThrowable { store.displayName = newDisplayName }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("보이는 이름이 없다면 이름이 보여진다")
    @Test
    fun returnNameWhenDisplayNameIsNull() {
        // given
        val store = StoreMother.get(autoFill = false)

        // when

        // then
        assertThat(store.displayedName).isEqualTo(store.name)
    }

    @DisplayName("보이는 이름이 있다면 보이는 이름이 보여진다")
    @Test
    fun returnDisplayNameWhenDisplayNameIsNotNull() {
        // given
        val store = StoreMother.get()

        // when

        // then
        assertThat(store.displayedName).isEqualTo(store.displayName)
    }

    @DisplayName("새로운 주소가 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun throwExceptionWhenNewAddressIsEmpty(address: String) {
        // given
        val store = StoreMother.get()

        // when
        val throwable = catchThrowable { store.address = address }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 주소가 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNewAddressLengthGreaterThanMaxLength() {
        // given
        val store = StoreMother.get()
        val address = "x".repeat(Store.MAX_ADDRESS_LENGTH + 1)

        // when
        val throwable = catchThrowable { store.address = address }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("정상적인 경우 객체가 생성된다")
    @Test
    fun createInstantWhenValidInput() {
        // given
        val name = "validName"

        // when
        val category = StoreMother.get { it.name = name }

        // then
        assertThat(category).isNotNull
    }
}
