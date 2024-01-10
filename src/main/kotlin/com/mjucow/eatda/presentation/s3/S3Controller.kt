package com.mjucow.eatda.presentation.s3

import com.mjucow.eatda.domain.s3.dto.PresignedUrlDto
import com.mjucow.eatda.domain.s3.service.S3Service
import com.mjucow.eatda.presentation.common.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/s3")
@RestController
class S3Controller(
    private val s3Service: S3Service,
) {

    @GetMapping("/presigned-url")
    fun getPutPresignedUrl(
        @RequestParam key: String,
        @RequestParam contentType: String,
    ): ApiResponse<PresignedUrlDto> {
        return ApiResponse.success(
            PresignedUrlDto(
                s3Service.createPutPresignedUrl(
                    key = key,
                    contentType = contentType
                )
            )
        )
    }
}
