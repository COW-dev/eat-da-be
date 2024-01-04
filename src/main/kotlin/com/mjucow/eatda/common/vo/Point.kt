package com.mjucow.eatda.common.vo

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
@Schema(
    name = "Point",
    description = "Represents a geographical point with latitude and longitude",
    example = """{"latitude": 37.5802219, "longitude": 126.9226047}"""
)
data class Point(

    @Column(name = "location_latitude")
    val latitude: Double,

    @Column(name = "location_longitude")
    val longitude: Double,
)
