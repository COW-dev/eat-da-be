package com.mjucow.eatda.domain.notice.service.query.dto

import com.mjucow.eatda.domain.notice.entity.Notice

data class NoticeDto(
    val id: Long,
    val title: String,
    val content: String,
) {
    companion object {
        fun from(domain: Notice): NoticeDto {
            return NoticeDto(
                domain.id,
                domain.title,
                domain.content
            )
        }
    }
}
