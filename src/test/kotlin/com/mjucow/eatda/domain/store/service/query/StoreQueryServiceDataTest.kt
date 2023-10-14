package com.mjucow.eatda.domain.store.service.query

import autoparams.kotlin.AutoKotlinSource
import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.store.entity.Category
import com.mjucow.eatda.domain.store.entity.Store
import com.mjucow.eatda.domain.store.entity.objectmother.StoreMother
import com.mjucow.eatda.persistence.store.CategoryRepository
import com.mjucow.eatda.persistence.store.StoreRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Pageable
import java.util.stream.IntStream

@Import(value = [StoreQueryService::class])
class StoreQueryServiceDataTest : AbstractDataTest() {
    @Autowired
    lateinit var storeQueryService: StoreQueryService

    @Autowired
    lateinit var repository: StoreRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @DisplayName("id에 해당하는 store가 없다면 null을 반환한다")
    @ParameterizedTest
    @AutoKotlinSource
    fun returnNullWhenNotExistEntity(id: Long) {
        // given

        // when
        val dto = storeQueryService.findById(id)

        // then
        assertThat(dto).isNull()
    }

    @DisplayName("id에 해당하는 store를 반환한다")
    @Test
    fun findById() {
        // given
        val store = StoreMother.create()
        repository.save(store)

        // when
        val dto = storeQueryService.findById(storeId = store.id)

        // then
        assertThat(dto!!.id).isEqualTo(store.id)
    }

    @DisplayName("id값이 없다면 최신 데이터를 조회한다")
    @Test
    fun findAllByCursorWithNullId() {
        // given
        val pageSize = 2
        val latestId = IntStream.range(0, pageSize * 2).mapToLong {
            repository.save(Store(name = "validName$it", address = StoreMother.ADDRESS)).id
        }.max().asLong
        val page = Pageable.ofSize(pageSize)

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(page = page)

        // then
        assertThat(result).anyMatch { it.id == latestId }
    }

    @DisplayName("데이터가 페이지 크기보다 크다면 페이지 크기만큼만 조회된다")
    @Test
    fun findAllByCursorWithPage() {
        // given
        val pageSize = Store.MAX_NAME_LENGTH - 1
        repeat(Store.MAX_NAME_LENGTH) {
            repository.save(Store(name = "x".repeat(it + 1), address = StoreMother.ADDRESS))
        }
        val page = Pageable.ofSize(pageSize)

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(id = (pageSize * 2).toLong(), page = page)

        // then
        assertThat(result.content.size).isEqualTo(pageSize)
        assertThat(result.hasNext()).isTrue()
    }

    @DisplayName("조회할 결과가 일부라면 일부만 조회된다")
    @Test
    fun findAllByCursor() {
        // given
        val store = StoreMother.create()
        val storeId = repository.save(store).id
        val pageSize = 10
        val page = Pageable.ofSize(pageSize)

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(id = storeId + 1, page = page)

        // then
        assertThat(result.content.size).isLessThan(pageSize)
        assertThat(result.hasNext()).isFalse()
    }

    @DisplayName("조회할 데이터가 없다면 empty를 반환한다")
    @Test
    fun findEmptyResult() {
        // given
        val store = StoreMother.create()
        repository.save(store)
        val page = Pageable.ofSize(10)

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(id = store.id, page = page)

        // then
        assertThat(result).isEmpty()
    }

    @DisplayName("id값이 없다면 최신 데이터를 조회한다: 특정 카테고리")
    @Test
    fun categoryTest1() {
        // given
        val category = categoryRepository.save(Category("test"))
        val pageSize = 2
        val latestId = IntStream.range(0, pageSize * 2).mapToLong {
            val store = Store(name = "validName$it", address = StoreMother.ADDRESS)
            store.addCategory(category)
            repository.saveAndFlush(store).id
        }.max().asLong
        val page = Pageable.ofSize(pageSize)

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(categoryId = category.id, page = page)

        // then
        assertThat(result).anyMatch { it.id == latestId }
    }

    @DisplayName("데이터가 페이지 크기보다 크다면 페이지 크기만큼만 조회된다: 특정 카테고리")
    @Test
    fun categoryTest2() {
        // given
        val category = categoryRepository.save(Category("test"))
        val pageSize = Store.MAX_NAME_LENGTH - 1
        repeat(Store.MAX_NAME_LENGTH) {
            val store = Store(name = "x".repeat(it + 1), address = StoreMother.ADDRESS)
            store.addCategory(category)
            repository.saveAndFlush(store)
        }
        val page = Pageable.ofSize(pageSize)

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(
            categoryId = category.id,
            page = page
        )

        // then
        assertThat(result.content.size).isEqualTo(pageSize)
        assertThat(result.hasNext()).isTrue()
    }

    @DisplayName("조회할 결과가 일부라면 일부만 조회된다: 특정 카테고리")
    @Test
    fun categoryTest3() {
        // given
        val category = categoryRepository.save(Category("test"))
        val store = StoreMother.create()
        store.addCategory(category)
        val storeId = repository.save(store).id
        val pageSize = 10
        val page = Pageable.ofSize(pageSize)

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(
            categoryId = category.id,
            id = storeId + 1,
            page = page
        )

        // then
        assertThat(result.content.size).isLessThan(pageSize)
        assertThat(result.hasNext()).isFalse()
    }

    @DisplayName("조회할 데이터가 없다면 empty를 반환한다: 특정 카테고리")
    @Test
    fun categoryTest4() {
        // given
        val category = categoryRepository.save(Category("test"))
        val store = StoreMother.create()
        store.addCategory(category)
        repository.save(store)
        val page = Pageable.ofSize(10)

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(
            categoryId = category.id,
            id = store.id,
            page = page
        )

        // then
        assertThat(result).isEmpty()
    }
}