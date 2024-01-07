package com.mjucow.eatda.domain.store.service.command.dto

import io.swagger.v3.oas.annotations.media.Schema

data class MenuCreateCommand(
    @Schema(name = "name", example = "고추바사삭")
    val name: String,
    @Schema(name = "price", example = "20000")
    val price: Int,
    @Schema(name = "imageAddress", example = "null")
    val imageAddress: String? = null,
)
