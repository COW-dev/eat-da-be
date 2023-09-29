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
abstract class BaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = DEFAULT_ID,
) {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseEntity
        if (id == DEFAULT_ID || other.id == DEFAULT_ID) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        const val DEFAULT_ID = 0L
        val ZONE_ID: ZoneId = ZoneId.of("Asia/Seoul")
    }
}
