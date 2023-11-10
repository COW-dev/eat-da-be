package com.mjucow.eatda.domain.banner.entity.objectmother

import com.mjucow.eatda.common.objectmother.EntityMother
import com.mjucow.eatda.domain.banner.entity.Banner
import java.time.Instant
import java.time.temporal.ChronoUnit

object BannerMother : EntityMother<Banner>() {
    const val LINK = "https://career.programmers.co.kr/competitions/3353"
    const val IMAGE_ADDRESS = "/banner/232D8241-C6A9-4AD9-B0EA-56F6DD24BADF.png"
    val EXPIRED_AT = Instant.now().plus(1, ChronoUnit.DAYS)!!

    override fun createFillInstance() = Banner(
        link = LINK,
        imageAddress = IMAGE_ADDRESS,
        expiredAt = EXPIRED_AT
    )

    override fun createDefaultInstance() = Banner(
        link = LINK,
        imageAddress = IMAGE_ADDRESS,
        expiredAt = EXPIRED_AT
    )
}
