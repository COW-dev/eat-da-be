package com.mjucow.eatda.common.vo

import com.mjucow.eatda.common.vo.deseializer.Latitude
import com.mjucow.eatda.common.vo.deseializer.Longitude
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Point(
    @Column(name = "location_latitude") val latitude: Latitude,
    @Column(name = "location_longitude") val longitude: Longitude,
)
