package com.mjucow.eatda.domain.store.service.command.dto

import io.swagger.v3.oas.annotations.media.Schema

data class MenuUpdateCommand(
    @Schema(name = "name", example = "뿌링클")
    val name: String,
    @Schema(name = "price", example = "18000")
    val price: Int,
    @Schema(name = "imageAddress", example = "null")
    val imageAddress: String? = null,
)
