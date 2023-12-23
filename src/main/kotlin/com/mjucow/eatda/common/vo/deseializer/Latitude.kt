package com.mjucow.eatda.common.vo.deseializer

import com.fasterxml.jackson.annotation.JsonProperty

@JvmInline
value class Latitude(
    @JsonProperty(value = "latitude")
    val value : Double
){
    init {
        if (value < -90 || value > 90) {
            throw IllegalArgumentException()
        }
    }
}
