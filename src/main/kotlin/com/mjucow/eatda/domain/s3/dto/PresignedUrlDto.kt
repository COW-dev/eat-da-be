package com.mjucow.eatda.domain.s3.dto

data class PresignedUrlDto(
    val url: String,
) {

    companion object {
        fun from(url: String): PresignedUrlDto {
            return PresignedUrlDto(url)
        }
    }
}
