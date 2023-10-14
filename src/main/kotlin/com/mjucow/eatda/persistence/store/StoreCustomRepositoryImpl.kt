package com.mjucow.eatda.persistence.store

import com.mjucow.eatda.domain.store.entity.Store
import com.mjucow.eatda.jooq.Tables.STORE
import com.mjucow.eatda.jooq.Tables.STORE_CATEGORY
import org.jooq.DSLContext
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository
import kotlin.math.min

@Repository
class StoreCustomRepositoryImpl(
    private val db: DSLContext,
) : StoreCustomRepository {
    override fun findIdsByCategoryIdOrderByIdDesc(categoryId: Long, page: Pageable, id: Long?): Slice<Long> {
        val query = db.select(STORE_CATEGORY.STORE_ID)
            .from(STORE_CATEGORY)
            .where(STORE_CATEGORY.CATEGORY_ID.eq(categoryId))

        if (id != null) {
            query.and(STORE_CATEGORY.STORE_ID.lessThan(id))
        }

        val result = query.orderBy(STORE_CATEGORY.STORE_ID.desc())
            .limit(page.pageSize + 1)
            .fetch()
            .into(Long::class.java)

        val content = result.subList(0, min(result.size, page.pageSize))
        val hasNext = result.size > page.pageSize

        return SliceImpl(content, page, hasNext)
    }

    override fun findAllByIdLessThanOrderByIdDesc(page: Pageable, id: Long?): Slice<Store> {
        val query = db.select()
            .from(STORE)

        if (id != null) {
            query.where(STORE.ID.lessThan(id))
        }

        val result = query.orderBy(STORE.ID.desc())
            .limit(page.pageSize + 1)
            .fetch()
            .into(Store::class.java)

        val content = result.subList(0, min(result.size, page.pageSize))
        val hasNext = result.size > page.pageSize

        return SliceImpl(content, page, hasNext)
    }
}
