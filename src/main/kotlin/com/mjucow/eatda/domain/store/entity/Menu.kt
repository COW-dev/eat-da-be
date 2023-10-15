package com.mjucow.eatda.domain.store.entity

import com.mjucow.eatda.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "menu")
class Menu(
    @JoinColumn(name = "store_id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val store: Store,
) : BaseEntity() {
    constructor(
        name: String,
        price: Int,
        imageAddress: String? = null,
        store: Store,
    ) : this(store) {
        this.name = name
        this.price = price
        this.imageAddress = imageAddress
    }

    @Column(nullable = false)
    var name: String = ""
        set(value) {
            validateName(value)
            field = value.trim()
        }

    @Column(nullable = false)
    var price: Int = 0
        set(value) {
            validatePrice(value)
            field = value
        }

    @Column(nullable = true)
    var imageAddress: String? = null
        set(value) {
            field = value?.also { validateImageAddress(it) }
        }

    private fun validateName(name: String) {
        require(name.isNotBlank() && name.length <= MAX_NAME_LENGTH)
    }

    private fun validatePrice(price: Int) {
        require(MIN_PRICE <= price && price % PRICE_UNIT == 0)
    }

    private fun validateImageAddress(imageAddress: String) {
        require(imageAddress.isNotBlank() && imageAddress.length <= MAX_IMAGE_ADDRESS_LENGTH)
    }

    companion object {
        const val MIN_PRICE = 100
        const val PRICE_UNIT = 100
        const val MAX_NAME_LENGTH = 31
        const val MAX_IMAGE_ADDRESS_LENGTH = 255
    }
}
