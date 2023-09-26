package com.mjucow.eatda.domain.store.category

import com.mjucow.eatda.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "category")
class Category(
    name: String,
) : BaseEntity() {
    @Column(nullable = false, unique = true, updatable = false)
    var name: String = name
        protected set
}
