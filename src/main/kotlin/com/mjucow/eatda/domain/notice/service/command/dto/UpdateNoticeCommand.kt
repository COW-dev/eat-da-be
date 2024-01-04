package com.mjucow.eatda.domain.notice.service.command.dto

import io.swagger.v3.oas.annotations.media.Schema

data class UpdateNoticeCommand(
    @Schema(name = "title", example = "newTitle")
    val title: String,
    @Schema(name = "content", example = "newContent")
    val content: String,
)
