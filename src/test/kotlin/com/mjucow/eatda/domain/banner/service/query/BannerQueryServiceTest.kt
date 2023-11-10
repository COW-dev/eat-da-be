package com.mjucow.eatda.domain.banner.service.query

import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.banner.entity.objectmother.BannerMother
import com.mjucow.eatda.persistence.banner.BannerRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(BannerQueryService::class)
class BannerQueryServiceTest : AbstractDataTest() {
    @Autowired
    lateinit var bannerQueryService: BannerQueryService

    @Autowired
    lateinit var repository: BannerRepository

    @DisplayName("배너가 없을 경우 빈 배열을 반환한다")
    @Test
    fun task1() {
        // given

        // when
        val banners = bannerQueryService.findAll()

        // then
        Assertions.assertThat(banners).isEmpty()
    }

    @DisplayName("전체 배너를 반환한다")
    @Test
    fun returnCategories() {
        // given
        repository.save(BannerMother.create())

        // when
        val banners = bannerQueryService.findAll()

        // then
        Assertions.assertThat(banners).isNotEmpty
    }
}
