package com.mjucow.eatda.presentation.notice.dto

import com.mjucow.eatda.domain.notice.Notice
import java.time.LocalDateTime

data class NoticeResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
) {
    constructor(notice: Notice) : this(
        notice.id!!,
        notice.title,
        notice.content,
        notice.createdAt
    )
}
