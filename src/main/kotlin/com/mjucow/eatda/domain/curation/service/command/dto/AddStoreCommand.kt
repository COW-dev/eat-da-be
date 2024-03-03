package com.mjucow.eatda.domain.curation.service.command.dto

import io.swagger.v3.oas.annotations.media.Schema

data class AddStoreCommand(
    @Schema(name = "storeId", example = "1")
    val storeId: Long,
)
