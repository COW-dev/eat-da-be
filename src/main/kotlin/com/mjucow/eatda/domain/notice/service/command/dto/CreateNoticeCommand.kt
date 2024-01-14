package com.mjucow.eatda.domain.notice.service.command.dto

import io.swagger.v3.oas.annotations.media.Schema

data class CreateNoticeCommand(
    @Schema(name = "title", example = "공지사항 제목")
    val title: String,
    @Schema(name = "content", example = "공지사항 내용")
    val content: String,
)
