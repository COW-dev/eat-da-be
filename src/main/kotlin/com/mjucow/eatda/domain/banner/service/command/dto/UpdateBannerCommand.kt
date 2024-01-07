package com.mjucow.eatda.domain.banner.service.command.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant

data class UpdateBannerCommand(
    @Schema(name = "link", example = "https://career.programmers.co.kr/competitions/3353")
    val link: String,
    @Schema(name = "imageAddress", example = "banner/232D8241-C6A9-4AD9-B0EA-56F6DD24BADF.png")
    val imageAddress: String,
    @Schema(name = "expiredAt", example = "2023-11-21T14:11:34.639184Z")
    val expiredAt: Instant?,
)
