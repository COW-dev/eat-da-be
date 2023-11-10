package com.mjucow.eatda.domain.banner.service.command

import autoparams.kotlin.AutoKotlinSource
import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.banner.entity.objectmother.BannerMother
import com.mjucow.eatda.domain.banner.service.command.dto.CreateBannerCommand
import com.mjucow.eatda.domain.banner.service.command.dto.UpdateBannerCommand
import com.mjucow.eatda.persistence.banner.BannerRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import java.time.temporal.ChronoUnit

@Import(BannerCommandService::class)
class BannerCommandServiceTest : AbstractDataTest() {
    @Autowired
    lateinit var bannerCommandService: BannerCommandService

    @Autowired
    lateinit var repository: BannerRepository

    @DisplayName("정상적인 값이 들어오면 배너가 생성된다")
    @Test
    fun task1() {
        // given
        val command = CreateBannerCommand(
            link = BannerMother.LINK,
            imageAddress = BannerMother.IMAGE_ADDRESS,
            expiredAt = BannerMother.EXPIRED_AT
        )

        // when
        val bannerId = bannerCommandService.create(command)

        // then
        Assertions.assertThat(repository.getReferenceById(bannerId)).isNotNull()
    }

    @DisplayName("정상적인 값이 들어오면 배너 정보가 수정된다")
    @Test
    fun task2() {
        // given
        val banner = repository.save(BannerMother.create())
        val updatedLink = BannerMother.LINK + "x"
        val updatedImageAddress = BannerMother.IMAGE_ADDRESS + "x"
        val updatedExpiredAt = BannerMother.EXPIRED_AT.plus(1, ChronoUnit.DAYS)
        val command = UpdateBannerCommand(
            link = updatedLink,
            imageAddress = updatedImageAddress,
            expiredAt = updatedExpiredAt
        )

        // when
        bannerCommandService.update(banner.id, command)

        // then
        val updatedBanner = repository.getReferenceById(banner.id)
        assertAll(
            { Assertions.assertThat(updatedBanner.link).isEqualTo(updatedLink) },
            { Assertions.assertThat(updatedBanner.imageAddress).isEqualTo(updatedImageAddress) },
            { Assertions.assertThat(updatedBanner.expiredAt).isEqualTo(updatedExpiredAt) }
        )
    }

    @DisplayName("삭제하려는 대상이 없어도 예외를 던지지 않는다")
    @ParameterizedTest
    @AutoKotlinSource
    fun task3(bannerId: Long) {
        // given

        // when
        val throwable = Assertions.catchThrowable { bannerCommandService.delete(bannerId) }

        // then
        Assertions.assertThat(throwable).isNull()
    }

    @DisplayName("삭제하려는 대상을 삭제한다")
    @Test
    fun task4() {
        // given
        val banner = BannerMother.create()
        repository.save(banner)

        // when
        bannerCommandService.delete(banner.id)

        // then
        Assertions.assertThat(repository.existsById(banner.id)).isFalse()
    }
}
