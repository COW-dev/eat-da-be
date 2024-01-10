package com.mjucow.eatda.domain.s3.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration

@Service
class S3Service(
    private val s3Presigner: S3Presigner,
    @Value("\${aws.s3.bucket}")
    private val bucket: String,
) {

    fun createPresignedUrl(key: String, contentType: String): String {
        val putObjectRequest = PutObjectRequest
            .builder()
            .bucket(bucket)
            .key(key)
            .contentType(contentType)
            .build()

        val presignRequest = PutObjectPresignRequest
            .builder()
            .signatureDuration(Duration.ofMinutes(DURATION_MINUTES))
            .putObjectRequest(putObjectRequest)
            .build()

        return s3Presigner.presignPutObject(presignRequest).url().toString()
    }

    companion object {
        const val DURATION_MINUTES = 3L
    }
}
