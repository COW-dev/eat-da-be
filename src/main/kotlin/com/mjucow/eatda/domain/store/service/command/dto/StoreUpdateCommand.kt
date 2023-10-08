package com.mjucow.eatda.domain.store.service.command.dto

import com.mjucow.eatda.common.vo.PhoneNumber
import com.mjucow.eatda.common.vo.Point

data class StoreUpdateCommand(
    val name: String,
    val address: String,
    val displayName: String?,
    val phoneNumber: PhoneNumber?,
    val imageAddress: String?,
    val location: Point?,
)
