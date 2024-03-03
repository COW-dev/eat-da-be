package com.mjucow.eatda.domain.store.service.query

import autoparams.kotlin.AutoKotlinSource
import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.curation.entity.Curation
import com.mjucow.eatda.domain.poplarstore.service.PopularStoreCommandService
import com.mjucow.eatda.domain.store.entity.Category
import com.mjucow.eatda.domain.store.entity.Store
import com.mjucow.eatda.domain.store.entity.objectmother.StoreMother
import com.mjucow.eatda.persistence.curation.CurationRepository
import com.mjucow.eatda.persistence.store.CategoryRepository
import com.mjucow.eatda.persistence.store.StoreRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import java.util.stream.IntStream

@Import(value = [StoreQueryService::class])
@ExtendWith(MockKExtension::class)
class StoreQueryServiceDataTest : AbstractDataTest() {
    @MockkBean(relaxUnitFun = true)
    lateinit var popularStoreCommandService: PopularStoreCommandService

    @Autowired
    lateinit var storeQueryService: StoreQueryService

    @Autowired
    lateinit var repository: StoreRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Autowired
    lateinit var curationRepository: CurationRepository

    @DisplayName("id에 해당하는 store가 없다면 예외를 던진다")
    @ParameterizedTest
    @AutoKotlinSource
    fun throwExceptionWhenNotExistEntity(id: Long) {
        // given

        // when
        val throwable = catchThrowable { storeQueryService.findById(id) }

        // then
        assertThat(throwable).isNotNull()
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
        assertThat(dto.id).isEqualTo(store.id)
    }

    @DisplayName("id값이 없다면 최신 데이터를 조회한다")
    @Test
    fun findAllByCursorWithNullId() {
        // given
        val pageSize = 2
        val latestId = IntStream.range(0, pageSize * 2).mapToLong {
            repository.save(
                Store(
                    name = "validName$it",
                    address = StoreMother.ADDRESS
                )
            ).id
        }.max().asLong

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(size = pageSize)

        // then
        assertThat(result).anyMatch { it.id == latestId }
    }

    @DisplayName("데이터가 페이지 크기보다 크다면 페이지 크기 + 1만큼만 조회된다")
    @Test
    fun findAllByCursorWithPage() {
        // given
        val pageSize = Store.MAX_NAME_LENGTH - 1
        repeat(Store.MAX_NAME_LENGTH) {
            repository.save(
                Store(
                    name = "x".repeat(it + 1),
                    address = StoreMother.ADDRESS
                )
            )
        }

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(cursor = (pageSize * 2).toLong(), size = pageSize)

        // then
        assertThat(result.size).isEqualTo(pageSize + 1)
    }

    @DisplayName("조회할 결과가 일부라면 일부만 조회된다")
    @Test
    fun findAllByCursor() {
        // given
        val store = StoreMother.create()
        val storeId = repository.save(store).id
        val pageSize = 10

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(cursor = storeId + 1, size = pageSize)

        // then
        assertThat(result.size).isLessThan(pageSize)
    }

    @DisplayName("조회할 데이터가 없다면 empty를 반환한다")
    @Test
    fun findEmptyResult() {
        // given
        val pageSize = 10
        val store = StoreMother.create()
        repository.save(store)

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(cursor = store.id, size = pageSize)

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

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(
            categoryId = category.id,
            size = pageSize
        )

        // then
        assertThat(result).anyMatch { it.id == latestId }
    }

    @DisplayName("데이터가 페이지 크기보다 크다면 페이지 크기 + 1만큼만 조회된다: 특정 카테고리")
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

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(categoryId = category.id, size = pageSize)

        // then
        assertThat(result.size).isEqualTo(pageSize + 1)
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

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(categoryId = category.id, cursor = storeId + 1, size = pageSize)

        // then
        assertThat(result.size).isLessThan(pageSize)
    }

    @DisplayName("조회할 데이터가 없다면 empty를 반환한다: 특정 카테고리")
    @Test
    fun categoryTest4() {
        // given
        val category = categoryRepository.save(Category("test"))
        val store = StoreMother.create()
        store.addCategory(category)
        repository.save(store)

        // when
        val result = storeQueryService.findAllByCategoryAndCursor(categoryId = category.id, cursor = store.id, size = 10)

        // then
        assertThat(result).isEmpty()
    }

    @DisplayName("데이터가 페이지 크기보다 크다면 페이지 크기 + 1만큼만 조회된다: 특정 큐레이션")
    @Test
    fun curationTest1() {
        // given
        val curation = curationRepository.save(Curation("test", "test", "test"))
        val pageSize = Store.MAX_NAME_LENGTH - 1
        repeat(Store.MAX_NAME_LENGTH) {
            val store = Store(name = "x".repeat(it + 1), address = StoreMother.ADDRESS)
            curation.addStore(store)
            repository.saveAndFlush(store)
            curationRepository.saveAndFlush(curation)
        }

        // when
        val result = storeQueryService.findAllByCurationAndCursor(curationId = curation.id, size = pageSize)

        // then
        assertThat(result.size).isEqualTo(pageSize + 1)
    }

    @DisplayName("조회할 결과가 일부라면 일부만 조회된다: 특정 큐레이션")
    @Test
    fun curationTest2() {
        // given
        val curation = curationRepository.save(Curation("test", "test", "test"))
        val store = StoreMother.create()
        curation.addStore(store)
        val storeId = repository.save(store).id
        val pageSize = 10

        // when
        val result = storeQueryService.findAllByCurationAndCursor(curationId = curation.id, cursor = storeId + 1, size = pageSize)

        // then
        assertThat(result.size).isLessThan(pageSize)
    }

    @DisplayName("조회할 데이터가 없다면 empty를 반환한다: 특정 큐레이션")
    @Test
    fun curationTes3() {
        // given
        val curation = curationRepository.save(Curation("test", "test", "test"))
        val store = StoreMother.create()
        curation.addStore(store)
        repository.save(store)

        // when
        val result = storeQueryService.findAllByCurationAndCursor(curationId = curation.id, cursor = store.id, size = 10)

        // then
        assertThat(result).isEmpty()
    }
}
