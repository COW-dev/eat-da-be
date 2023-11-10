package com.mjucow.eatda.domain.banner.service.command.dto

import java.time.Instant

data class UpdateBannerCommand(
    val link: String,
    val imageAddress: String,
    val expiredAt: Instant?,
)
