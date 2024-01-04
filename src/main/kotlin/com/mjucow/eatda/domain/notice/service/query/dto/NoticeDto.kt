package com.mjucow.eatda.domain.notice.service.query.dto

import com.mjucow.eatda.domain.notice.entity.Notice
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant

data class NoticeDto(
    @Schema(name = "id", example = "1")
    val id: Long,
    @Schema(name = "title", example = "공지사항 제목")
    val title: String,
    @Schema(name = "content", example = "공지사항 내용")
    val content: String,
    @Schema(name = "createdAt", example = "2023-11-20T14:11:53.025126Z")
    val createdAt: Instant,
) {
    companion object {
        fun from(domain: Notice): NoticeDto {
            return NoticeDto(
                domain.id,
                domain.title,
                domain.content,
                domain.createdAt
            )
        }
    }
}
