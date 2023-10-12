package com.mjucow.eatda.domain.notice.service.command

import com.mjucow.eatda.domain.notice.entity.Notice
import com.mjucow.eatda.domain.notice.service.command.dto.CreateNoticeCommand
import com.mjucow.eatda.domain.notice.service.command.dto.UpdateNoticeCommand
import com.mjucow.eatda.persistence.notice.NoticeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class NoticeCommandService(
    private val repository: NoticeRepository,
) {
    fun create(request: CreateNoticeCommand): Long {
        return repository.save(Notice(request.title, request.content)).id
    }

    fun update(noticeId: Long, command: UpdateNoticeCommand) {
        val (newTitle, newContent) = command
        val notice = repository.findByIdOrNull(noticeId)?.apply {
            title = newTitle
            content = newContent
        } ?: throw IllegalArgumentException()

        repository.save(notice)
    }

    fun deleteById(noticeId: Long) {
        val notice = repository.findByIdOrNull(noticeId) ?: return

        repository.delete(notice)
    }
}
