package com.mjucow.eatda.domain.s3.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.net.URL
import java.time.Duration

@Service
class S3Service(
    private val s3Presigner: S3Presigner,
    @Value("\${aws.s3.bucket}")
    private val bucket: String,
) {

    fun createPutPresignedUrl(key: String, contentType: String): URL {
        val putObjectRequest = PutObjectRequest
            .builder()
            .bucket(bucket)
            .key(key)
            .contentType(contentType)
            .build()

        val presignRequest = PutObjectPresignRequest
            .builder()
            .signatureDuration(Duration.ofMinutes(UPLOAD_DURATION_MINUTES))
            .putObjectRequest(putObjectRequest)
            .build()

        return s3Presigner.presignPutObject(presignRequest).url()
    }

    fun createGetPresignedUrl(key: String): URL {
        val getObjectRequest = GetObjectRequest
            .builder()
            .bucket(bucket)
            .key(key)
            .build()

        val presignRequest = GetObjectPresignRequest
            .builder()
            .signatureDuration(Duration.ofHours(DOWNLOAD_DURATION_HOURS))
            .getObjectRequest(getObjectRequest)
            .build()

        return s3Presigner.presignGetObject(presignRequest).url()
    }

    companion object {
        const val UPLOAD_DURATION_MINUTES = 3L
        const val DOWNLOAD_DURATION_HOURS = 24L
    }
}
