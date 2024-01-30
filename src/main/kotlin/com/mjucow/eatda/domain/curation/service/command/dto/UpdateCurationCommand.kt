package com.mjucow.eatda.domain.curation.service.command.dto

import io.swagger.v3.oas.annotations.media.Schema

data class UpdateCurationCommand(
    @Schema(name = "title", example = "수정할 큐레이션 제목")
    val title: String,
    @Schema(name = "description", example = "수정할 큐레이션 설명")
    val description: String,
)
