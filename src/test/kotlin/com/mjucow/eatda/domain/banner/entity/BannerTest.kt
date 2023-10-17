package com.mjucow.eatda.domain.banner.entity

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource

class BannerTest {
    @DisplayName("링크 주소가 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun task1(link: String) {
        // given
        val displayOrder = 0
        val imageAddress = "imageAddress"

        // when
        val throwable = Assertions.catchThrowable { Banner(displayOrder, link, imageAddress) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("링크 주소가 정규식을 만족하지 않을 경우 예외를 던진다")
    @Test
    fun task2() {
        // given
        val displayOrder = 0
        val link = "link"
        val imageAddress = "imageAddress"

        // when
        val throwable = Assertions.catchThrowable { Banner(displayOrder, link, imageAddress) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("링크 주소가 최대 길이보다 길 경우 예외를 던진다")
    @Test
    fun task3() {
        // given
        val displayOrder = 0
        val link = "x".repeat(Banner.MAX_LINK_LENGTH + 1)
        val imageAddress = "imageAddress"

        // when
        val throwable = Assertions.catchThrowable { Banner(displayOrder, link, imageAddress) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("이미지 경로가 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun task4(imageAddress: String) {
        // given
        val displayOrder = 0
        val link = "https://career.programmers.co.kr/competitions/3353"

        // when
        val throwable = Assertions.catchThrowable { Banner(displayOrder, link, imageAddress) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("이미지 경로가 최대 길이보다 길 경우 예외를 던진다")
    @Test
    fun task5() {
        // given
        val displayOrder = 0
        val link = "link"
        val imageAddress = "x".repeat(Banner.MAX_IMAGE_ADDRESS_LENGTH + 1)

        // when
        val throwable = Assertions.catchThrowable { Banner(displayOrder, link, imageAddress) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("정상적인 값일 경우 객체가 생성된다")
    @Test
    fun task6() {
        // given
        val displayOrder = 0
        val link = "https://career.programmers.co.kr/competitions/3353"
        val imageAddress = "imageAddress"

        // when
        val banner = Banner(
            displayOrder,
            link,
            imageAddress
        )

        // then
        assertThat(banner).isNotNull()
    }
}