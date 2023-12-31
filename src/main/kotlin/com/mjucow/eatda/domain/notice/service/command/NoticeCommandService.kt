package com.mjucow.eatda.domain.notice.service.command

import com.mjucow.eatda.domain.notice.entity.Notice
import com.mjucow.eatda.domain.notice.service.command.dto.CreateNoticeCommand
import com.mjucow.eatda.domain.notice.service.command.dto.UpdateNoticeCommand
import com.mjucow.eatda.persistence.notice.NoticeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class NoticeCommandService(
    private val repository: NoticeRepository,
) {
    fun create(command: CreateNoticeCommand): Long {
        return repository.save(Notice(command.title, command.content)).id
    }

    fun update(noticeId: Long, command: UpdateNoticeCommand) {
        val (newTitle, newContent) = command
        val updatedNotice = repository.getReferenceById(noticeId).apply {
            title = newTitle
            content = newContent
        }

        repository.save(updatedNotice)
    }

    fun delete(noticeId: Long) {
        val notice = repository.findByIdOrNull(noticeId) ?: return

        repository.delete(notice)
    }
}
