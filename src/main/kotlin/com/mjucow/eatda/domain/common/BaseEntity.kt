package com.mjucow.eatda.domain.common

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.ZoneId

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
        protected set

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(ZONE_ID)
        protected set

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(ZONE_ID)
        protected set

    companion object {
        val ZONE_ID: ZoneId = ZoneId.of("Asia/Seoul")
    }
}
