package com.mjucow.eatda.domain.banner.entity

import com.mjucow.eatda.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.net.URL

@Entity
@Table(name = "banner")
class Banner() : BaseEntity() {
    constructor(
        displayOrder: Int,
        link: String,
        imageAddress: String,
    ) : this() {
        this.displayOrder = displayOrder
        this.link = link
        this.imageAddress = imageAddress
    }

    @Column(nullable = false)
    var displayOrder: Int = 0
        set(value) {
            validateDisplayOrder(value)
            field = value
        }

    @Column(nullable = false)
    var link: String = ""
        set(value) {
            validateLink(value)
            field = value
        }

    @Column(nullable = false)
    var imageAddress: String = ""
        set(value) {
            validateImageAddress(value)
            field = value
        }

    private fun isValidUrl(url: String): Boolean {
        return runCatching {
            URL(url)
        }.isSuccess
    }

    private fun validateDisplayOrder(displayOrder: Int) {
        //
    }

    private fun validateLink(link: String) {
        require(link.isNotBlank() && isValidUrl(link) && link.length <= MAX_LINK_LENGTH)
    }

    private fun validateImageAddress(imageAddress: String) {
        require(imageAddress.isNotBlank() && imageAddress.length <= MAX_IMAGE_ADDRESS_LENGTH)
    }

    companion object {
        const val MAX_LINK_LENGTH = 255
        const val MAX_IMAGE_ADDRESS_LENGTH = 255
    }
}