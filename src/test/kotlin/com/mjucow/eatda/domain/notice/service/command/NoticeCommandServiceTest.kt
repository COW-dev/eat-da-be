package com.mjucow.eatda.domain.notice.service.command

import autoparams.kotlin.AutoKotlinSource
import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.notice.entity.Notice
import com.mjucow.eatda.domain.notice.service.command.dto.CreateNoticeCommand
import com.mjucow.eatda.domain.notice.service.command.dto.UpdateNoticeCommand
import com.mjucow.eatda.persistence.notice.NoticeRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull

@Import(NoticeCommandService::class)
class NoticeCommandServiceTest : AbstractDataTest() {
    @Autowired
    lateinit var noticeCommandService: NoticeCommandService

    @Autowired
    lateinit var repository: NoticeRepository

    @DisplayName("제목이 공백일 경우 예외를 반환한다")
    @ParameterizedTest
    @EmptySource
    fun task1(title: String) {
        // given
        val content = "content"
        val command = CreateNoticeCommand(title, content)

        // when
        val throwable = Assertions.catchThrowable { noticeCommandService.create(command) }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("내용이 공백일 경우 예외를 반환한다")
    @ParameterizedTest
    @EmptySource
    fun task2(content: String) {
        // given
        val title = "title"
        val command = CreateNoticeCommand(title, content)

        // when
        val throwable = Assertions.catchThrowable { noticeCommandService.create(command) }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("정상적인 입력일 경우 객체가 반환한다")
    @Test
    fun task3() {
        // given
        val title = "title"
        val content = "content"
        val command = CreateNoticeCommand(title, content)

        // when
        val domain = noticeCommandService.create(command)

        // then
        Assertions.assertThat(domain).isNotNull
    }

    @DisplayName("새로운 제목이 공백일 경우 예외를 반환한다")
    @ParameterizedTest
    @EmptySource
    fun task4(newTitle: String) {
        // given
        val noticeId = 1L
        val newContent = "newContent"
        val command = UpdateNoticeCommand(newTitle, newContent)

        // when
        val throwable = Assertions.catchThrowable { noticeCommandService.update(noticeId, command) }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 내용이 공백일 경우 예외를 반환한다")
    @ParameterizedTest
    @EmptySource
    fun task5(newContent: String) {
        // given
        val noticeId = 1L
        val newTitle = "newTitle"
        val command = UpdateNoticeCommand(newTitle, newContent)

        // when
        val throwable = Assertions.catchThrowable { noticeCommandService.update(noticeId, command) }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("수정하려는 대상이 조회되지 않을 경우 예외를 반환한다")
    @Test
    fun task6() {
        // given
        val noticeId = 1L
        val newTitle = "newTitle"
        val newContent = "newContent"
        val command = UpdateNoticeCommand(newTitle, newContent)

        // when
        val throwable = Assertions.catchThrowable { noticeCommandService.update(noticeId, command) }

        // then
        Assertions.assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("정상적인 입력일 경우 수정을 성공한다")
    @Test
    fun task7() {
        // given
        val title = "title"
        val content = "content"
        val noticeId = repository.save(Notice(title, content)).id
        val newTitle = "newTitle"
        val command = UpdateNoticeCommand(newTitle, content)

        // when
        noticeCommandService.update(noticeId, command)

        // then
        Assertions.assertThat(repository.getReferenceById(noticeId).title).isEqualTo(newTitle)
    }

    @DisplayName("저장된 데이터가 없어도 삭제된다")
    @ParameterizedTest
    @AutoKotlinSource
    fun task8(noticeId: Long) {
        // given

        // when
        noticeCommandService.deleteById(noticeId)

        // then
        Assertions.assertThat(repository.findByIdOrNull(noticeId)).isNull()
    }

    @DisplayName("저장된 데이터가 삭제된다")
    @Test
    fun task9() {
        // given
        val title = "title"
        val content = "content"
        val notcieId = repository.save(Notice(title, content)).id

        // when
        noticeCommandService.deleteById(notcieId)

        // then
        Assertions.assertThat(repository.findByIdOrNull(notcieId)).isNull()
    }
}
