package com.mjucow.eatda.domain.store.entity.objectmother

import com.mjucow.eatda.domain.common.BaseEntity
import org.springframework.test.util.ReflectionTestUtils
import java.util.concurrent.atomic.AtomicLong

abstract class EntityMother<T : BaseEntity> : ObjectMother<T>() {

    protected val idGenerator = AtomicLong(BaseEntity.DEFAULT_ID + 1)

    fun createWithId(
        autoFill: Boolean = false,
        id: Long = BaseEntity.DEFAULT_ID,
        apply: (T) -> Unit = {},
    ): T {
        val instance = create(autoFill, apply)
        ReflectionTestUtils.setField(
            instance,
            "id",
            if (id != BaseEntity.DEFAULT_ID) id else idGenerator.getAndIncrement()
        )
        return instance.apply(apply)
    }
}
