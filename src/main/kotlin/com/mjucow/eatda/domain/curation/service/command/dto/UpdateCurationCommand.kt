package com.mjucow.eatda.domain.curation.service.command.dto

import io.swagger.v3.oas.annotations.media.Schema

data class UpdateCurationCommand(
    @Schema(name = "title", example = "newTitle")
    val title: String,
    @Schema(name = "description", example = "newDescription")
    val description: String,
)
