package com.mjucow.eatda.presentation.notice

import com.mjucow.eatda.domain.notice.service.command.dto.CreateNoticeCommand
import com.mjucow.eatda.domain.notice.service.command.dto.UpdateNoticeCommand
import com.mjucow.eatda.domain.notice.service.query.dto.NoticeDto
import com.mjucow.eatda.domain.notice.service.query.dto.Notices
import com.mjucow.eatda.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "공지사항 API", description = "공지사항을 관리해주는 API")
interface NoticeApiPresentation {

    @Operation(summary = "공지사항 생성", description = "공지사항을 생성합니다.")
    fun create(command: CreateNoticeCommand): ApiResponse<Long>

    @Operation(summary = "공지사항 전체조회", description = "모든 공지사항을 조회합니다.")
    fun findAll(): ApiResponse<Notices>

    @Operation(summary = "공지사항 단건조회", description = "하나의 공지사항을 조회합니다.")
    fun findById(noticeId: Long): ApiResponse<NoticeDto>

    @Operation(summary = "공지사항 수정", description = "공지사항을 수정합니다.")
    fun update(noticeId: Long, command: UpdateNoticeCommand)

    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    fun deleteById(noticeId: Long)
}
