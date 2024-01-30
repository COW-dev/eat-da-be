package com.mjucow.eatda.domain.curation.service.command.dto

import io.swagger.v3.oas.annotations.media.Schema

data class CreateCurationCommand(
    @Schema(name = "title", example = "큐레이션 제목")
    val title: String,
    @Schema(name = "description", example = "큐레이션 설명")
    val description: String,
)
