package com.mjucow.eatda.domain.store.service.command.dto

import io.swagger.v3.oas.annotations.media.Schema

data class CreateCommand(
    @Schema(name = "name", description = "생성할 카테고리 이름", example = "validName")
    val name: String,
)
