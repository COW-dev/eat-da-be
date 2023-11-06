package com.mjucow.eatda.domain.banner.service.query.dto

import com.mjucow.eatda.domain.banner.entity.Banner
import java.time.Instant

data class BannerDto(
    val id: Long,
    val link: String,
    val imageAddress: String,
    val expiredAt: Instant?,
    val createdAt: Instant,
) {
    companion object {
        fun from(domain: Banner): BannerDto {
            return BannerDto(
                domain.id,
                domain.link,
                domain.imageAddress,
                domain.expiredAt,
                domain.createdAt
            )
        }
    }
}
