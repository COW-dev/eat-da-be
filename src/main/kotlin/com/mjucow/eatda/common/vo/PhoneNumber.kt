package com.mjucow.eatda.common.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

/**
 * <p>전화번호를 다루는 Value Object</p>
 * <p>전화번호의 형식만 검증하기 때문에 실제 존재하는 전화번호가 아닐 수 있다</p>
 */
@Embeddable
data class PhoneNumber(@Column(name = "phone-number") val value: String) {
    init {
        NUMBER_REGEX.matchEntire(value) ?: throw IllegalArgumentException()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhoneNumber

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        val NUMBER_REGEX = Regex("^0\\d{1,2}-\\d{3,4}-\\d{4}\$")
    }
}
