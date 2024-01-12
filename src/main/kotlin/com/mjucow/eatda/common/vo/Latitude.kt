package com.mjucow.eatda.common.vo

import com.fasterxml.jackson.annotation.JsonProperty

@JvmInline
value class Latitude(
    @JsonProperty(value = "latitude")
    val value: Double,
) {
    init {
        if (LATITUDE_MIN > value ||  LATITUDE_MAX < value) {
            throw IllegalArgumentException()
        }
    }

    companion object{
        private const val LATITUDE_MIN = -90.0
        private const val LATITUDE_MAX = 90.0
    }
}
