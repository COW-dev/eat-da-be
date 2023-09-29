package com.mjucow.eatda.domain.store.entity

import com.mjucow.eatda.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "category")
class Category() : BaseEntity() {
    constructor(name: String) : this() {
        validateName(name)
        this.name = name
    }

    @Column(nullable = false, unique = true)
    var name: String = ""
        set(value) {
            validateName(value)
            field = value
        }

    private fun validateName(name: String) {
        require(name.isNotBlank() && name.trim().length <= MAX_NAME_LENGTH)
    }
    companion object {
        const val MAX_NAME_LENGTH = 31
    }

}
