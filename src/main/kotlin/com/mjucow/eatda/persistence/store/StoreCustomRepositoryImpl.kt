package com.mjucow.eatda.persistence.store

import com.mjucow.eatda.domain.store.entity.Store
import com.mjucow.eatda.jooq.Tables.CURATION_STORE
import com.mjucow.eatda.jooq.Tables.STORE
import com.mjucow.eatda.jooq.Tables.STORE_CATEGORY
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class StoreCustomRepositoryImpl(
    private val db: DSLContext,
) : StoreCustomRepository {
    override fun findIdsByCategoryIdOrderByIdDesc(categoryId: Long, size: Int, id: Long?): List<Long> {
        val query = db.select(STORE_CATEGORY.STORE_ID)
            .from(STORE_CATEGORY)
            .where(STORE_CATEGORY.CATEGORY_ID.eq(categoryId))

        if (id != null) {
            query.and(STORE_CATEGORY.STORE_ID.lessThan(id))
        }

        return query.orderBy(STORE_CATEGORY.STORE_ID.desc())
            .limit(size + 1)
            .fetch()
            .into(Long::class.java)
            .toList()
    }

    override fun findIdsByCurationIdOrderByIdDesc(curationId: Long, size: Int, id: Long?): List<Long> {
        val query = db.select(CURATION_STORE.STORE_ID)
            .from(CURATION_STORE)
            .where(CURATION_STORE.CURATION_ID.eq(curationId))

        if (id != null) {
            query.and(CURATION_STORE.STORE_ID.lessThan(id))
        }

        return query.orderBy(CURATION_STORE.STORE_ID.desc())
            .limit(size + 1)
            .fetch()
            .into(Long::class.java)
            .toList()
    }

    override fun findAllByIdLessThanOrderByIdDesc(size: Int, id: Long?): List<Store> {
        val query = db.select()
            .from(STORE)

        if (id != null) {
            query.where(STORE.ID.lessThan(id))
        }

        return query.orderBy(STORE.ID.desc())
            .limit(size + 1)
            .fetch()
            .into(Store::class.java)
            .toList()
    }
}
