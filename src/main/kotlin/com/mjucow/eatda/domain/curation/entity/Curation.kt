package com.mjucow.eatda.domain.curation.entity

import com.mjucow.eatda.domain.common.BaseEntity
import com.mjucow.eatda.domain.store.entity.Store
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "curation")
class Curation() : BaseEntity() {
    constructor(
        title: String,
        description: String,
        imageAddress: String,
    ) : this() {
        this.title = title
        this.description = description
        this.imageAddress = imageAddress
    }

    @Column(nullable = false)
    var title: String = ""
        set(value) {
            validateTitle(value)
            field = value
        }

    @Column(nullable = false)
    var description: String = ""
        set(value) {
            validateDescription(value)
            field = value
        }

    @Column(nullable = false)
    var imageAddress: String = ""

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "curation_store",
        joinColumns = [JoinColumn(name = "store_id")],
        inverseJoinColumns = [JoinColumn(name = "curation_id")]
    )
    val mutableStores: MutableSet<Store> = mutableSetOf()

    fun getStores(): Set<Store> = mutableStores.toSet()

    fun addStore(store: Store) {
        mutableStores.add(store)
    }

    private fun validateTitle(title: String) {
        require(title.isNotBlank() && title.length <= MAX_TITLE_LENGTH)
    }

    private fun validateDescription(description: String) {
        require(description.isNotBlank() && description.length <= MAX_DESCRIPTION_LENGTH)
    }

    companion object {
        const val MAX_TITLE_LENGTH = 255
        const val MAX_DESCRIPTION_LENGTH = 255
    }
}
