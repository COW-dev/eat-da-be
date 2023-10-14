package com.mjucow.eatda.domain.store.service.command.dto

data class MenuUpdateCommand(
    val id: Long,
    val name: String,
    val price: Int,
    val imageAddress: String? = null,
)
