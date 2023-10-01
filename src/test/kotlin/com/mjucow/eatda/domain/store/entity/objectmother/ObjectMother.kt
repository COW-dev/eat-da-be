package com.mjucow.eatda.domain.store.entity.objectmother

abstract class ObjectMother<T> {
    fun create(autoFill: Boolean = false, apply: (T) -> Unit = {}): T {
        val instance = if (autoFill) createFillInstance() else createDefaultInstance()
        return instance.apply(apply)
    }

    abstract fun createFillInstance(): T
    abstract fun createDefaultInstance(): T
}
