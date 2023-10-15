package com.mjucow.eatda.domain.notice.service.query

import com.mjucow.eatda.domain.notice.service.query.dto.NoticeDto
import com.mjucow.eatda.domain.notice.service.query.dto.Notices
import com.mjucow.eatda.persistence.notice.NoticeRepository
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
        return NoticeDto.from(repository.getReferenceById(noticeId))
    }
}
