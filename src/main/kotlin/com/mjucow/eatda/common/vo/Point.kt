package com.mjucow.eatda.common.vo

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Point(

    @Column(name = "location_latitude")
    @Schema(name = "latitude", example = "37.5802219")
    val latitude: Double,

    @Column(name = "location_longitude")
    @Schema(name = "longitude", example = "126.9226047")
    val longitude: Double,
)
