package com.mjucow.eatda.domain.notice.entity

import com.mjucow.eatda.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "notice")
class Notice() : BaseEntity() {
    constructor(
        title: String,
        content: String,
    ) : this() {
        this.title = title
        this.content = content
    }

    @Column(nullable = false)
    var title: String = ""
        set(value) {
            validateValue(value)
            field = value
        }

    @Column(nullable = false)
    var content: String = ""
        set(value) {
            validateValue(value)
            field = value
        }

    private fun validateValue(value: String) {
        require(value.isNotBlank())
    }
}