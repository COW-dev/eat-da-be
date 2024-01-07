package com.mjucow.eatda.domain.store.service.command.dto

import io.swagger.v3.oas.annotations.media.Schema

data class UpdateNameCommand(
    @Schema(name = "name", description = "수정할 카테고리 이름", example = "newName")
    val name: String,
)
