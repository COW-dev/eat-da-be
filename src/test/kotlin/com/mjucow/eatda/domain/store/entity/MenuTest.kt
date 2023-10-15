package com.mjucow.eatda.domain.store.entity

import com.mjucow.eatda.domain.store.entity.objectmother.MenuMother
import com.mjucow.eatda.domain.store.entity.objectmother.StoreMother
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.junit.jupiter.params.provider.NullSource
import org.junit.jupiter.params.provider.ValueSource

class MenuTest {
    @DisplayName("이름이 빈 값이라면 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun test1(name: String) {
        // given
        val store = StoreMother.create()

        // when
        val throwable = catchThrowable {
            Menu(name = name, price = MenuMother.PRICE, store = store)
        }

        // then
        assertThat(throwable).isNotNull()
    }

    @DisplayName("이름이 길이가 최대길이보다 길면 예외를 던진다")
    @Test
    fun test2() {
        // given
        val store = StoreMother.create()
        val name = "a".repeat(Menu.MAX_NAME_LENGTH + 1)

        // when
        val throwable = catchThrowable {
            Menu(name = name, price = MenuMother.PRICE, store = store)
        }

        // then
        assertThat(throwable).isNotNull()
    }

    @DisplayName("가격이 최소 가격보다 작다면 예외를 던진다")
    @ParameterizedTest
    @ValueSource(ints = [Int.MIN_VALUE, -1, 0, 1, Menu.MIN_PRICE - 1])
    fun test3(price: Int) {
        // given
        val store = StoreMother.create()

        // when
        val throwable = catchThrowable {
            Menu(name = MenuMother.NAME, price = price, store = store)
        }

        // then
        assertThat(throwable).isNotNull()
    }

    @DisplayName("가격이 단위 가격을 준수하지 않으면 예외를 던진다")
    @Test
    fun test4() {
        // given
        val store = StoreMother.create()
        val price = MenuMother.PRICE + Menu.PRICE_UNIT - 1

        // when
        val throwable = catchThrowable {
            Menu(name = MenuMother.NAME, price = price, store = store)
        }

        // then
        assertThat(throwable).isNotNull()
    }

    @DisplayName("이미지 주소가 빈 값이라면 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun test4(imageAddress: String) {
        // given
        val store = StoreMother.create()

        // when
        val throwable = catchThrowable {
            Menu(
                name = MenuMother.NAME,
                price = MenuMother.PRICE,
                imageAddress = imageAddress,
                store = store
            )
        }

        // then
        assertThat(throwable).isNotNull()
    }

    @DisplayName("이미지 주소가 최대 길이보다 길다면 예외를 던진다")
    @Test
    fun test5() {
        // given
        val store = StoreMother.create()
        val imageAddress = "a".repeat(Menu.MAX_IMAGE_ADDRESS_LENGTH + 1)

        // when
        val throwable = catchThrowable {
            Menu(
                name = MenuMother.NAME,
                price = MenuMother.PRICE,
                imageAddress = imageAddress,
                store = store
            )
        }

        // then
        assertThat(throwable).isNotNull()
    }

    @DisplayName("정상적인 값이라면 생성된다")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = [MenuMother.IMAGE_ADDRESS])
    fun test6(imageAddress: String?) {
        // given
        val store = StoreMother.create()

        // when
        val menu = Menu(
            name = MenuMother.NAME,
            price = MenuMother.PRICE,
            imageAddress = imageAddress,
            store = store
        )

        // then
        assertThat(menu).isNotNull()
    }
}
