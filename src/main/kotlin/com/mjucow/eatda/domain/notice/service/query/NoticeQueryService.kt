package com.mjucow.eatda.domain.notice.service.query

import com.mjucow.eatda.domain.notice.service.query.dto.NoticeDto
import com.mjucow.eatda.domain.notice.service.query.dto.Notices
import com.mjucow.eatda.persistence.notice.NoticeRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class NoticeQueryService(
    private val repository: NoticeRepository,
) {
    fun findAll(): Notices {
        return Notices(repository.findAllByOrderByIdDesc().map(NoticeDto::from))
    }

    fun findById(noticeId: Long): NoticeDto {
        return repository.findByIdOrNull(noticeId)?.let {
            NoticeDto.from(it)
        } ?: throw EntityNotFoundException("공지사항이 존재하지 않습니다.")
    }
}