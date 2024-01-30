package com.mjucow.eatda.domain.s3.dto

import java.net.URL

data class PresignedUrlDto(
    val url: URL,
) {

    companion object {
        fun from(url: URL): PresignedUrlDto {
            return PresignedUrlDto(url)
        }
    }
}
