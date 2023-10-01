package com.mjucow.eatda.domain.store.entity

import com.mjucow.eatda.common.vo.PhoneNumber
import com.mjucow.eatda.common.vo.Point
import com.mjucow.eatda.domain.common.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "store")
class Store() : BaseEntity() {
    constructor(
        name: String,
        address: String,
        displayName: String? = null,
        phoneNumber: PhoneNumber? = null,
        location: Point? = null,
    ) : this() {
        this.name = name.also { validateName(name) }
        this.address = address.also { validateAddress(address) }
        this.displayName = displayName?.let {
            validateName(displayName)
            displayName.trim()
        }
        this.phoneNumber = phoneNumber
        this.location = location
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

    @Column(nullable = false)
    var address: String = ""
        set(value) {
            validateAddress(value)
            field = value
        }

    @Embedded
    var phoneNumber: PhoneNumber? = null

    @Column(nullable = true)
    var imageAddress: String? = null

    @Embedded
    var location: Point? = null

    val displayedName: String
        get() = displayName ?: name

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "store_id")
    protected val mutableStoreHours: MutableList<StoreHours> = mutableListOf()

    fun getStoreHours(): List<StoreHours> = mutableStoreHours.toList()

    fun addStoreHour(newHours: StoreHours) {
        // TODO: 겹치는 시간이 있는 지 확인하기
        if (mutableStoreHours.any {
            // 같은 날에 시간이 겹치는 경우, 날짜는 다르지만 새벽 영업(36시간 제도)으로 시간이 겹치는 경우
            (it.dayOfWeek == newHours.dayOfWeek && (it.openAt <= newHours.closeAt || newHours.openAt <= it.closeAt))
                || (it.dayOfWeek.isNextDayOf(newHours.dayOfWeek) && (newHours.openAt + StoreHours.ONE_DAY_MINUTE) <= it.closeAt)
                || (it.dayOfWeek.isPrevtDayOf(newHours.dayOfWeek) && (it.openAt + StoreHours.ONE_DAY_MINUTE) <= newHours.closeAt)
        }) {
            throw IllegalArgumentException()
        }

        mutableStoreHours.add(newHours)
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "store_category",
        joinColumns = [JoinColumn(name = "store_id")],
        inverseJoinColumns = [JoinColumn(name = "category_id")]
    )
    protected val mutableCategories: MutableSet<Category> = mutableSetOf()

    fun getCategories(): Set<Category> = mutableCategories.toSet()

    fun addCategory(category: Category) {
        mutableCategories.add(category)
    }

    private fun validateName(name: String) {
        validateString(name, MAX_NAME_LENGTH)
    }

    private fun validateAddress(address: String) {
        validateString(address, MAX_ADDRESS_LENGTH)
    }

    private fun validateString(value: String, maxLength: Int, minLength: Int = 0) {
        require(value.isNotBlank() && value.trim().length in minLength..maxLength)
    }

    companion object {
        const val MAX_NAME_LENGTH = 31
        const val MAX_ADDRESS_LENGTH = 63
    }
}
