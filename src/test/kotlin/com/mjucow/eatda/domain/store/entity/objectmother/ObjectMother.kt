package com.mjucow.eatda.domain.store.entity.objectmother

abstract class ObjectMother<T> {

    protected abstract val minimumInstance: T

    protected abstract val fillInstance: T

    fun get(autoFill:Boolean=true, apply: (T) -> Unit = {}): T {
        val instance = if(autoFill) fillInstance else minimumInstance
        return instance.apply(apply)
    }
}
