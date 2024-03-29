package com.mjucow.eatda.common.vo

import com.fasterxml.jackson.annotation.JsonProperty

@JvmInline
value class Longitude(
    @JsonProperty(value = "longitude")
    val value: Double,
) {
    init {
        if (LONGITUDE_MIN > value || LONGITUDE_MAX < value) {
            throw IllegalArgumentException()
        }
    }

    companion object {
        const val LONGITUDE_MIN = 124.0
        const val LONGITUDE_MAX = 132.0
    }
}
