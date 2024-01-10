package com.mjucow.eatda.common.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
class S3Config(
    @Value("\${aws.s3.credentials.access-key}")
    private val accessKey: String,
    @Value("\${aws.s3.credentials.secret-key}")
    private val secretKey: String,
) {

    @Bean
    fun s3Presigner(): S3Presigner {
        val credential = AwsBasicCredentials.create(accessKey, secretKey)

        return S3Presigner.builder()
            .region(Region.AP_NORTHEAST_2)
            .credentialsProvider { credential }
            .build()
    }
}
