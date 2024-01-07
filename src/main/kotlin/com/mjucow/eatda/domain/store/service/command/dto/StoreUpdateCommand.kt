package com.mjucow.eatda.domain.store.service.command.dto

import com.mjucow.eatda.common.vo.PhoneNumber
import com.mjucow.eatda.common.vo.Point
import io.swagger.v3.oas.annotations.media.Schema

data class StoreUpdateCommand(
    @Schema(name = "name", example = "명지대학교")
    val name: String,
    @Schema(name = "address", example = "서울특별시 서대문구 거북골로 34")
    val address: String,
    @Schema(name = "displayName", example = "띵지대")
    val displayName: String?,
    @Schema(name = "phoneNumber", example = "02-300-1656")
    val phoneNumber: PhoneNumber?,
    @Schema(name = "imageAddress", example = "/eatda/public/store/232D8241-C6A9-4AD9-B0EA-56F6DD24BADF.jpg")
    val imageAddress: String?,
    @Schema(name = "location")
    val location: Point?,
)
