package com.mjucow.eatda.common.vo

import com.fasterxml.jackson.annotation.JsonProperty
@JvmInline
value class Longitude(
    @JsonProperty(value = "longitude")
    val value: Double,
) {
    init {
        if (value !in 0.0..180.0) {
            throw IllegalArgumentException()
        }
    }
}
