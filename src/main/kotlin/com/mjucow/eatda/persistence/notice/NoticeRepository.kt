package com.mjucow.eatda.persistence.notice

import com.mjucow.eatda.domain.notice.entity.Notice
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepository : JpaRepository<Notice, Long> {
    fun findAllByOrderByCreatedAtDesc(): List<Notice>
}
