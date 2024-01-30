package com.mjucow.eatda.domain.curation.entity

import com.mjucow.eatda.domain.curation.entity.objectmother.CurationMother
import com.mjucow.eatda.domain.store.entity.objectmother.StoreMother
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.springframework.test.util.ReflectionTestUtils

class CurationTest {
    @DisplayName("제목이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun task1(title: String) {
        // given

        // when
        val throwable = Assertions.catchThrowable {
            Curation(
                title = title,
                description = CurationMother.DESCRIPTION
            )
        }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("제목이 최대 길이보다 길 경우 예외를 던진다")
    @Test
    fun task2() {
        // given
        val title = "x".repeat(Curation.MAX_TITLE_LENGTH + 1)

        // when
        val throwable = Assertions.catchThrowable {
            Curation(
                title = title,
                description = CurationMother.DESCRIPTION
            )
        }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("설명이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun task3(description: String) {
        // given

        // when
        val throwable = Assertions.catchThrowable {
            Curation(
                title = CurationMother.TITLE,
                description = description,
            )
        }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("설명이 최대 길이보다 길 경우 예외를 던진다")
    @Test
    fun task4() {
        // given
        val description = "x".repeat(Curation.MAX_DESCRIPTION_LENGTH + 1)

        // when
        val throwable = Assertions.catchThrowable {
            Curation(
                title = CurationMother.TITLE,
                description = description,
            )
        }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 가게를 추가할 수 있다")
    @Test
    fun task5() {
        // given
        val store = StoreMother.create()
        val curation = CurationMother.create()
        ReflectionTestUtils.setField(store, "id", 1L)

        // when
        curation.addStore(store)

        // then
        Assertions.assertThat(curation.getStores()).contains(store)
    }

    @DisplayName("이미 존재하는 가게는 중복 저장되지 않는다")
    @Test
    fun task6() {
        // given
        val store = StoreMother.create()
        val curation = CurationMother.create { it.addStore(store) }
        val prevStoreCount = curation.getStores().size

        // when
        curation.addStore(store)

        // then
        Assertions.assertThat(curation.getStores().size).isEqualTo(prevStoreCount)
    }

    @DisplayName("정상적인 값일 경우 객체가 생성된다")
    @Test
    fun task7() {
        // given

        // when
        val curation = Curation(
            title = CurationMother.TITLE,
            description = CurationMother.DESCRIPTION
        )

        // then
        Assertions.assertThat(curation).isNotNull()
    }
}
