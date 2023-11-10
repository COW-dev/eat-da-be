package com.mjucow.eatda.domain.banner.entity

import com.mjucow.eatda.domain.banner.entity.objectmother.BannerMother
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import java.time.Instant
import java.time.temporal.ChronoUnit

class BannerTest {
    @DisplayName("링크 주소가 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun task1(link: String) {
        // given

        // when
        val throwable = Assertions.catchThrowable { Banner(link, BannerMother.IMAGE_ADDRESS, BannerMother.EXPIRED_AT) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("링크 주소가 정규식을 만족하지 않을 경우 예외를 던진다")
    @Test
    fun task2() {
        // given
        val link = "link"

        // when
        val throwable = Assertions.catchThrowable { Banner(link, BannerMother.IMAGE_ADDRESS, BannerMother.EXPIRED_AT) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("링크 주소가 최대 길이보다 길 경우 예외를 던진다")
    @Test
    fun task3() {
        // given
        val link = "x".repeat(Banner.MAX_LINK_LENGTH + 1)

        // when
        val throwable = Assertions.catchThrowable {
            Banner(
                link,
                BannerMother.IMAGE_ADDRESS,
                BannerMother.EXPIRED_AT
            )
        }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("이미지 경로가 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun task4(imageAddress: String) {
        // given

        // when
        val throwable = Assertions.catchThrowable { Banner(BannerMother.LINK, imageAddress, BannerMother.EXPIRED_AT) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("이미지 경로가 최대 길이보다 길 경우 예외를 던진다")
    @Test
    fun task5() {
        // given
        val imageAddress = "x".repeat(Banner.MAX_IMAGE_ADDRESS_LENGTH + 1)

        // when
        val throwable = Assertions.catchThrowable { Banner(BannerMother.LINK, imageAddress, BannerMother.EXPIRED_AT) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("설정한 만료일자가 현재보다 빠를 경우 예외를 던진다")
    @Test
    fun task6() {
        // given
        val expiredAt = Instant.now().minus(1, ChronoUnit.DAYS)

        // when
        val throwable = Assertions.catchThrowable { Banner(BannerMother.LINK, BannerMother.IMAGE_ADDRESS, expiredAt) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("설정한 만료일자가 최대 값보다 클 경우 예외를 던진다")
    @Test
    fun task7() {
        // given
        val expiredAt = Instant.MAX

        // when
        val throwable = Assertions.catchThrowable { Banner(BannerMother.LINK, BannerMother.IMAGE_ADDRESS, expiredAt) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("정상적인 값일 경우 객체가 생성된다")
    @Test
    fun task8() {
        // given

        // when
        val banner = Banner(
            BannerMother.LINK,
            BannerMother.IMAGE_ADDRESS,
            BannerMother.EXPIRED_AT
        )

        // then
        assertThat(banner).isNotNull()
    }
}
