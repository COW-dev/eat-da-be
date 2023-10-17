package com.mjucow.eatda.domain.notice.entity

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource

class NoticeTest {
    @DisplayName("제목이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun task1(title: String) {
        // given
        val content = "content"

        // when
        val throwable = Assertions.catchThrowable { Notice(title, content) }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("내용이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun task2(content: String) {
        // given
        val title = "title"

        // when
        val throwable = Assertions.catchThrowable { Notice(title, content) }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 제목이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun task3(newTitle: String) {
        // given
        val notice = Notice("title", "content")

        // when
        val throwable = Assertions.catchThrowable { notice.title = newTitle }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 내용이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun task4(newContent: String) {
        // given
        val notice = Notice("title", "content")

        // when
        val throwable = Assertions.catchThrowable { notice.content = newContent }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("정상적인 값일 경우 객체가 생성된다")
    @Test
    fun task5() {
        // given
        val title = "title"
        val content = "content"

        // when
        val notice = Notice(title, content)

        // then
        Assertions.assertThat(notice).isNotNull
    }
}
