package com.mjucow.eatda.domain.notice.service.query

import autoparams.kotlin.AutoKotlinSource
import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.notice.entity.Notice
import com.mjucow.eatda.persistence.notice.NoticeRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(NoticeQueryService::class)
class NoticeQueryServiceTest : AbstractDataTest() {
    @Autowired
    lateinit var noticeQueryService: NoticeQueryService

    @Autowired
    lateinit var repository: NoticeRepository

    @DisplayName("id에 해당하는 공지사항이 없을 경우 예외를 반환한다")
    @ParameterizedTest
    @AutoKotlinSource
    fun task1(noticeId: Long) {
        // given

        // when
        val throwable = Assertions.catchThrowable { noticeQueryService.findById(noticeId) }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("id에 해당하는 카테고리를 반환한다")
    @Test
    fun task2() {
        // given
        val saved = repository.save(Notice("title", "content"))

        // when
        val domain = noticeQueryService.findById(saved.id)

        // then
        Assertions.assertThat(domain.id).isEqualTo(saved.id)
    }

    @DisplayName("공지사항이 없을 경우 빈 배열을 반환한다")
    @Test
    fun task3() {
        // given

        // when
        val notices = noticeQueryService.findAll()

        // then
        Assertions.assertThat(notices).isEmpty()
    }

    @DisplayName("전체 공지사항을 반환한다")
    @Test
    fun returnCategories() {
        // given
        repository.save(Notice("title", "content"))

        // when
        val notices = noticeQueryService.findAll()

        // then
        Assertions.assertThat(notices).isNotEmpty
    }
}