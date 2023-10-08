package com.mjucow.eatda.domain.store.service.command.dto

import com.mjucow.eatda.common.vo.PhoneNumber
import com.mjucow.eatda.common.vo.Point

data class StoreCreateCommand(
    val name: String,
    val address: String,
    val displayName: String? = null,
    val phoneNumber: PhoneNumber? = null,
    val imageAddress: String? = null,
    val location: Point? = null,
)
