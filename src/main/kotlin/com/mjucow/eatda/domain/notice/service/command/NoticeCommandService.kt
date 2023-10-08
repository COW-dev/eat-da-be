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
        val notice = repository.findByIdOrNull(noticeId)
            ?: throw IllegalArgumentException()
        val (newTitle, newContent) = command
        notice.let {
            it.title = newTitle
            it.content = newContent
        }

        repository.save(notice)
    }

    fun deleteById(noticeId: Long) {
        val notice = repository.findByIdOrNull(noticeId) ?: return

        repository.delete(notice)
    }
}
