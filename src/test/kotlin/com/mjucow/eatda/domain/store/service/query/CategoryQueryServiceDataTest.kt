package com.mjucow.eatda.domain.store.service.query

import autoparams.kotlin.AutoKotlinSource
import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.store.entity.Category
import com.mjucow.eatda.persistence.store.CategoryRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(CategoryQueryService::class)
class CategoryQueryServiceDataTest: AbstractDataTest() {
    @Autowired
    lateinit var categoryQueryService: CategoryQueryService
    @Autowired
    lateinit var repository: CategoryRepository

    @DisplayName("id에 해당하는 카테고리가 없으면 null을 반환한다")
    @ParameterizedTest
    @AutoKotlinSource
    fun returnNullWhenNotFoundId(id: Long) {
        // given


        // when
        val domain = categoryQueryService.findById(id)

        // then
        assertThat(domain).isNull()
    }

    @DisplayName("id에 해당하는 카테고리를 반환한다")
    @Test
    fun returnDtoWhenValidId() {
        // given
        val saved = repository.save(Category("validName"))

        // when
        val domain = categoryQueryService.findById(saved.id)

        // then
        assertThat(domain!!.id).isEqualTo(saved.id)
    }

    @DisplayName("카테고리가 없으면 요소가 없는 카테고리들을 반환한다")
    @Test
    fun returnCategoriesWhenEmpty() {
        // given

        // when
        val cateogries = categoryQueryService.findAll()

        // then
        assertThat(cateogries).isEmpty()
    }

    @DisplayName("전체 카테고리를 반환한다")
    @Test
    fun returnCategories() {
        // given
        repository.save(Category("validName1"))

        // when
        val categories = categoryQueryService.findAll()

        // then
        assertThat(categories).isNotEmpty
    }

}
