package com.mjucow.eatda.domain.banner.service.command

import autoparams.kotlin.AutoKotlinSource
import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.banner.entity.objectmother.BannerMother
import com.mjucow.eatda.persistence.banner.BannerRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(BannerCommandService::class)
class BannerCommandServiceTest : AbstractDataTest() {
    @Autowired
    lateinit var bannerCommandService: BannerCommandService

    @Autowired
    lateinit var repository: BannerRepository

    @DisplayName("삭제하려는 대상이 없어도 예외를 던지지 않는다")
    @ParameterizedTest
    @AutoKotlinSource
    fun task1(bannerId: Long) {
        // given

        // when
        val throwable = Assertions.catchThrowable { bannerCommandService.delete(bannerId) }

        // then
        Assertions.assertThat(throwable).isNull()
    }

    @DisplayName("삭제하려는 대상을 삭제한다")
    @Test
    fun task2() {
        // given
        val banner = BannerMother.create()
        repository.save(banner)

        // when
        bannerCommandService.delete(banner.id)

        // then
        Assertions.assertThat(repository.existsById(banner.id)).isFalse()
    }
}
