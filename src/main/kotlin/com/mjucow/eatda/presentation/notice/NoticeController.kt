package com.mjucow.eatda.presentation.notice

import com.mjucow.eatda.domain.notice.service.command.NoticeCommandService
import com.mjucow.eatda.domain.notice.service.command.dto.CreateNoticeCommand
import com.mjucow.eatda.domain.notice.service.command.dto.UpdateNoticeCommand
import com.mjucow.eatda.domain.notice.service.query.NoticeQueryService
import com.mjucow.eatda.domain.notice.service.query.dto.NoticeDto
import com.mjucow.eatda.domain.notice.service.query.dto.Notices
import com.mjucow.eatda.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "공지사항 API", description = "공지사항을 관리해주는 API")
@RequestMapping("/api/v1/notices")
@RestController
class NoticeController(
    private val noticeQueryService: NoticeQueryService,
    private val noticeCommandService: NoticeCommandService,
) {

    @Operation(summary = "공지사항 생성", description = "공지사항을 생성합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody command: CreateNoticeCommand,
    ): ApiResponse<Long> {
        return ApiResponse.success(noticeCommandService.create(command))
    }

    @Operation(summary = "공지사항 전체조회", description = "모든 공지사항을 조회합니다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): ApiResponse<Notices> {
        return ApiResponse.success(noticeQueryService.findAll())
    }

    @Operation(summary = "공지사항 단건조회", description = "하나의 공지사항을 조회합니다.")
    @GetMapping("/{noticeId}")
    @ResponseStatus(HttpStatus.OK)
    fun findById(
        @PathVariable("noticeId") noticeId: Long,
    ): ApiResponse<NoticeDto> {
        return ApiResponse.success(noticeQueryService.findById(noticeId))
    }

    @Operation(summary = "공지사항 수정", description = "공지사항을 수정합니다.")
    @PatchMapping("/{noticeId}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @PathVariable("noticeId") noticeId: Long,
        @RequestBody command: UpdateNoticeCommand,
    ) {
        noticeCommandService.update(noticeId, command)
    }

    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    @DeleteMapping("/{noticeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(
        @PathVariable("noticeId") noticeId: Long,
    ) {
        noticeCommandService.delete(noticeId)
    }
}
