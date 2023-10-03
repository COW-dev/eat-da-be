package com.mjucow.eatda.common.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Point(
    @Column(name = "location_latitude") val latitude: Double,
    @Column(name = "location_longitude") val longitude: Double,
)
