package com.mjucow.eatda.domain.notice

import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepository : JpaRepository<Notice, Long> {
    fun findAllByOrderByCreatedAtDesc(): List<Notice>
}
