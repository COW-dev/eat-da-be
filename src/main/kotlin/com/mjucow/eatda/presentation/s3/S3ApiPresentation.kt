package com.mjucow.eatda.presentation.s3

import com.mjucow.eatda.domain.s3.dto.PresignedUrlDto
import com.mjucow.eatda.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "S3 API", description = "S3 관련 API")
interface S3ApiPresentation {

    @Operation(
        summary = "이미지 업로드용 presigned URL 발급",
        description = "*key: 버킷의 폴더 경로"
    )
    fun getPutPresignedUrl(key: String, contentType: String): ApiResponse<PresignedUrlDto>
}
