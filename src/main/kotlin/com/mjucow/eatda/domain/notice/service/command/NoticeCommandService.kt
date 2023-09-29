package com.mjucow.eatda.domain.notice.service.command

import com.mjucow.eatda.domain.notice.entity.Notice
import com.mjucow.eatda.domain.notice.service.command.dto.CreateNoticeCommand
import com.mjucow.eatda.domain.notice.service.command.dto.UpdateNoticeCommand
import com.mjucow.eatda.persistence.notice.NoticeRepository
import jakarta.persistence.EntityNotFoundException
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

    @Throws(EntityNotFoundException::class)
    fun update(noticeId: Long, request: UpdateNoticeCommand) {
        val notice = repository.findByIdOrNull(noticeId)
            ?: throw EntityNotFoundException("공지사항이 존재하지 않습니다.")
        val (newTitle, newContent) = request
        notice.let {
            if (newTitle != null) it.title = newTitle
            if (newContent != null) it.content = newContent
        }
        repository.save(notice)
    }

    fun deleteById(noticeId: Long) {
        val notice = repository.findByIdOrNull(noticeId) ?: throw IllegalArgumentException()
        repository.delete(notice)
    }
}
