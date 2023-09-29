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
@Table(name = "store")
class Store() : BaseEntity() {
    constructor(name: String, displayName: String? = null) : this() {
        this.name = name.also { validateName(name) }
        this.displayName = displayName?.let {
            validateName(displayName)
            displayName.trim()
        }
    }

    @Column(nullable = false, unique = true)
    var name: String = ""
        set(value) {
            validateName(value)
            field = value.trim()
        }

    @Column(nullable = true)
    var displayName: String? = null
        set(value) {
            field = value?.let {
                validateName(it)
                it.trim()
            }
        }

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "store_category",
        joinColumns = [JoinColumn(name = "store_id")],
        inverseJoinColumns = [JoinColumn(name = "category_id")],
    )
    protected val mutableCategories: MutableSet<Category> = mutableSetOf()

    fun getCategories(): Set<Category> {
        return mutableCategories.toSet()
    }

    fun addCategory(category: Category) {
        mutableCategories.add(category)
    }

    fun getDisplayedName() = displayName ?: name

    private fun validateName(name: String) {
        require(name.isNotBlank() && name.trim().length <= MAX_NAME_LENGTH)
    }

    companion object {
        const val MAX_NAME_LENGTH = 31
    }
}
