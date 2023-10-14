package com.mjucow.eatda.domain.store.service.command.dto

data class MenuCreateCommand(
    val storeId: Long,
    val name: String,
    val price: Int,
    val imageAddress: String? = null,
)
