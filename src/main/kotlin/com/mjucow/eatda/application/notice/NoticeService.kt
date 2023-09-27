package com.mjucow.eatda.application.notice

import com.mjucow.eatda.domain.notice.Notice
import com.mjucow.eatda.domain.notice.NoticeRepository
import com.mjucow.eatda.presentation.notice.dto.CreateNoticeRequest
import com.mjucow.eatda.presentation.notice.dto.NoticeIdResponse
import com.mjucow.eatda.presentation.notice.dto.NoticeResponse
import com.mjucow.eatda.presentation.notice.dto.UpdateNoticeRequest
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class NoticeService(
    private val noticeRepository: NoticeRepository,
) {
    fun create(request: CreateNoticeRequest): NoticeIdResponse {
        val notice = noticeRepository.save(Notice(request.title, request.content))
        return NoticeIdResponse(notice)
    }

    fun findAll(): List<NoticeResponse> {
        return noticeRepository.findAllByOrderByCreatedAtDesc().map { NoticeResponse(it) }
    }

    @Throws(EntityNotFoundException::class)
    fun findById(noticeId: Long): NoticeResponse {
        val notice = noticeRepository.findByIdOrNull(noticeId)
            ?: throw EntityNotFoundException("공지사항이 존재하지 않습니다.")
        return NoticeResponse(notice)
    }

    @Throws(EntityNotFoundException::class)
    fun update(noticeId: Long, request: UpdateNoticeRequest): NoticeIdResponse {
        val notice = noticeRepository.findByIdOrNull(noticeId)
            ?: throw EntityNotFoundException("공지사항이 존재하지 않습니다.")
        val (newTitle, newContent) = request
        notice.let {
            if (newTitle != null) it.title = newTitle
            if (newContent != null) it.content = newContent
        }
        return NoticeIdResponse(notice)
    }

    fun deleteById(noticeId: Long) {
        return noticeRepository.deleteById(noticeId)
    }
}