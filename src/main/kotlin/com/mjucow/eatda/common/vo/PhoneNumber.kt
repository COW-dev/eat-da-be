package com.mjucow.eatda.common.vo

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.mjucow.eatda.common.vo.deseializer.PhoneNumberDeserializer
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

/**
 * <p>전화번호를 다루는 Value Object</p>
 * <p>전화번호의 형식만 검증하기 때문에 실제 존재하는 전화번호가 아닐 수 있다</p>
 */
@Embeddable
@JsonDeserialize(using = PhoneNumberDeserializer::class)
data class PhoneNumber(
    @JsonProperty(value = "phoneNumber")
    @Column(name = "phone_number")
    @Schema(name = "phoneNumber", example = "02-300-1656")
    val value: String,
) {
    init {
        NUMBER_REGEX.matchEntire(value) ?: throw IllegalArgumentException()
    }

    companion object {
        val NUMBER_REGEX = Regex("^0\\d{1,2}[-)]\\d{3,4}-\\d{4}\$")
    }
}
