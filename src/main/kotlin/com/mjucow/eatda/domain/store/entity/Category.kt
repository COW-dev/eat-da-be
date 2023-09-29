package com.mjucow.eatda.domain.store.entity

import com.mjucow.eatda.domain.common.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "store_category",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "store_id")],
    )
    protected val mutableStores: MutableSet<Store> = mutableSetOf()

    fun getStores(): Set<Store> {
        return mutableStores.toSet()
    }

    private fun validateName(name: String) {
        require(name.isNotBlank() && name.trim().length <= MAX_NAME_LENGTH)
    }
    companion object {
        const val MAX_NAME_LENGTH = 31
    }
}
