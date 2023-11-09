package com.mjucow.eatda.domain.banner.service.command.dto

import java.time.Instant

data class CreateBannerCommand(
    val link: String,
    val imageAddress: String,
    val expiredAt: Instant? = null,
)
