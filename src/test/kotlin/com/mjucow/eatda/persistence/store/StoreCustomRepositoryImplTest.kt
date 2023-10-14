package com.mjucow.eatda.persistence.store

import com.mjucow.eatda.common.config.JacksonConfiguration
import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.store.entity.Category
import com.mjucow.eatda.domain.store.entity.Store
import com.mjucow.eatda.domain.store.entity.objectmother.StoreMother
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Pageable
import java.util.stream.IntStream

@Import(JacksonConfiguration::class)
class StoreCustomRepositoryImplTest : AbstractDataTest() {
    @Autowired
    lateinit var storeCustomRepositoryImpl: StoreCustomRepositoryImpl

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Autowired
    lateinit var storeRepository: StoreRepository

    @DisplayName("커버 방식의 가게 조회: 마지막 조회한 가게 식별자 X, 카테고리 식별자 O, 페이지 크기 대비 실제 데이터 많음")
    @Test
    fun test11() {
        // given
        val pageSize = 5
        val category = categoryRepository.saveAndFlush(Category("test"))

        val minStoreId = IntStream.range(0, pageSize + 1).mapToLong {
            val store = Store(name = "name$it", address = StoreMother.ADDRESS)
            val updated = storeRepository.saveAndFlush(store)
            updated.addCategory(category)
            storeRepository.saveAndFlush(updated).id
        }.min().asLong

        // when
        val result = storeCustomRepositoryImpl.findIdsByCategoryIdOrderByIdDesc(
            categoryId = category.id,
            page = Pageable.ofSize(pageSize)
        )

        // then
        assertThat(result).hasSize(pageSize).allMatch { it > minStoreId }
        assertThat(result.hasNext()).isTrue()
    }

    @DisplayName("커버 방식의 가게 조회: 마지막 조회한 가게 식별자 X, 카테고리 식별자 O, 페이지 크기 대비 실제 데이터 적음")
    @Test
    fun test12() {
        // given
        val pageSize = 5
        val dataSize = 3
        val category = categoryRepository.saveAndFlush(Category("test"))

        val minStoreId = IntStream.range(0, dataSize).mapToLong {
            val store = Store(name = "name$it", address = StoreMother.ADDRESS)
            val updated = storeRepository.saveAndFlush(store)
            updated.addCategory(category)
            storeRepository.saveAndFlush(updated).id
        }.min().asLong

        // when
        val result = storeCustomRepositoryImpl.findIdsByCategoryIdOrderByIdDesc(
            categoryId = category.id,
            page = Pageable.ofSize(pageSize)
        )

        // then
        assertThat(result).hasSize(dataSize).allMatch { it >= minStoreId }
        assertThat(result.hasNext()).isFalse()
    }

    @DisplayName("커버 방식의 가게 조회: 마지막 조회한 가게 식별자 X, 카테고리 식별자 O, 페이지 크기 대비 실제 데이터 없음")
    @Test
    fun test13() {
        // given
        val pageSize = 5
        val category = categoryRepository.saveAndFlush(Category("test"))

        // when
        val result = storeCustomRepositoryImpl.findIdsByCategoryIdOrderByIdDesc(
            categoryId = category.id,
            page = Pageable.ofSize(pageSize)
        )

        // then
        assertThat(result).isEmpty()
    }

    @DisplayName("커버 방식의 가게 조회: 마지막 조회한 가게 식별자 O, 카테고리 식별자 O, 페이지 크기 대비 실제 데이터 많음")
    @Test
    fun test21() {
        // given
        val pageSize = 5
        val category = categoryRepository.saveAndFlush(Category("test"))

        val maxStoreId = IntStream.range(0, pageSize * 2).mapToLong {
            val store = Store(name = "name$it", address = StoreMother.ADDRESS)
            val updated = storeRepository.saveAndFlush(store)
            updated.addCategory(category)
            storeRepository.saveAndFlush(updated).id
        }.max().asLong

        // when
        val result = storeCustomRepositoryImpl.findIdsByCategoryIdOrderByIdDesc(
            categoryId = category.id,
            page = Pageable.ofSize(pageSize),
            id = maxStoreId
        )

        // then
        assertThat(result).hasSize(pageSize).allMatch { it < maxStoreId }
        assertThat(result.hasNext()).isTrue()
    }

    @DisplayName("커버 방식의 가게 조회: 마지막 조회한 가게 식별자 O, 카테고리 식별자 O, 페이지 크기 대비 실제 데이터 적음")
    @Test
    fun test22() {
        // given
        val pageSize = 5
        val dataSize = 2
        val category = categoryRepository.saveAndFlush(Category("test"))

        val minStoreId = IntStream.range(0, pageSize * 2).mapToLong {
            val store = Store(name = "name$it", address = StoreMother.ADDRESS)
            val updated = storeRepository.saveAndFlush(store)
            updated.addCategory(category)
            storeRepository.saveAndFlush(updated).id
        }.min().asLong

        // when
        val result = storeCustomRepositoryImpl.findIdsByCategoryIdOrderByIdDesc(
            categoryId = category.id,
            page = Pageable.ofSize(pageSize),
            id = minStoreId + dataSize
        )

        // then
        assertThat(result).hasSize(dataSize).allMatch { it < minStoreId + dataSize }
        assertThat(result.hasNext()).isFalse()
    }

    @DisplayName("커버 방식의 가게 조회: 마지막 조회한 가게 식별자 O, 카테고리 식별자 O, 페이지 크기 대비 실제 데이터 없음")
    @Test
    fun test23() {
        // given
        val pageSize = 5
        val category = categoryRepository.saveAndFlush(Category("test"))

        // when
        val result = storeCustomRepositoryImpl.findIdsByCategoryIdOrderByIdDesc(
            categoryId = category.id,
            page = Pageable.ofSize(pageSize),
            id = Long.MAX_VALUE
        )

        // then
        assertThat(result).isEmpty()
    }

    @DisplayName("커버 방식의 가게 조회: 마지막 조회한 가게 식별자 O, 카테고리 식별자 X, 페이지 크기 대비 실제 데이터 많음")
    @Test
    fun test31() {
        // given
        val pageSize = 2
        val maxStoreId = IntStream.range(0, pageSize * 2).mapToLong {
            val store = Store(name = "name$it", address = StoreMother.ADDRESS)
            storeRepository.saveAndFlush(store).id
        }.max().asLong

        // when
        val result = storeCustomRepositoryImpl.findAllByIdLessThanOrderByIdDesc(
            page = Pageable.ofSize(pageSize),
            id = maxStoreId + 1
        )

        // then
        assertThat(result.content.size).isEqualTo(pageSize)
        assertThat(result.hasNext()).isTrue()
    }

    @DisplayName("커버 방식의 가게 조회: 마지막 조회한 가게 식별자 O, 카테고리 식별자 X, 페이지 크기 대비 실제 데이터 적음")
    @Test
    fun test32() {
        // given
        val pageSize = 5
        val dataSize = 2

        val maxStoreId = IntStream.range(0, dataSize).mapToLong {
            val store = Store(name = "name$it", address = StoreMother.ADDRESS)
            storeRepository.saveAndFlush(store).id
        }.max().asLong

        // when
        val result = storeCustomRepositoryImpl.findAllByIdLessThanOrderByIdDesc(
            page = Pageable.ofSize(pageSize),
            id = maxStoreId + 1
        )

        // then
        assertThat(result.content.size).isEqualTo(dataSize)
        assertThat(result.hasNext()).isFalse()
    }

    @DisplayName("커버 방식의 가게 조회: 마지막 조회한 가게 식별자 O, 카테고리 식별자 O, 페이지 크기 대비 실제 데이터")
    @Test
    fun test33() {
        // given
        val pageSize = 5

        // when
        val result = storeCustomRepositoryImpl.findAllByIdLessThanOrderByIdDesc(
            page = Pageable.ofSize(pageSize)
        )

        // then
        assertThat(result).isEmpty()
    }
}
