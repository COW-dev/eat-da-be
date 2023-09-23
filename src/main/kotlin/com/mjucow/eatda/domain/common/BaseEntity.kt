package com.mjucow.eatda.domain.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.ZoneId

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(ZONE_ID)
        protected set

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(ZONE_ID)
        protected set

    @PrePersist
    fun initTimeColumns() {
        createdAt = LocalDateTime.now(ZONE_ID)
        updatedAt = LocalDateTime.now(ZONE_ID)
    }

    @PreUpdate
    fun updateTimeColumn() {
        updatedAt = LocalDateTime.now(ZONE_ID)
    }

    companion object {
        val ZONE_ID: ZoneId = ZoneId.of("Asia/Seoul")
    }
}
