package com.mjucow.eatda.presentation.notice

import com.mjucow.eatda.domain.notice.service.command.NoticeCommandService
import com.mjucow.eatda.domain.notice.service.command.dto.CreateNoticeCommand
import com.mjucow.eatda.domain.notice.service.command.dto.UpdateNoticeCommand
import com.mjucow.eatda.domain.notice.service.query.NoticeQueryService
import com.mjucow.eatda.domain.notice.service.query.dto.NoticeDto
import com.mjucow.eatda.domain.notice.service.query.dto.Notices
import com.mjucow.eatda.presentation.common.ApiResponse
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

@RequestMapping("/api/v1/notices")
@RestController
class NoticeController(
    private val noticeQueryService: NoticeQueryService,
    private val noticeCommandService: NoticeCommandService,
) : NoticeApiPresentation {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    override fun create(
        @RequestBody command: CreateNoticeCommand,
    ): ApiResponse<Long> {
        return ApiResponse.success(noticeCommandService.create(command))
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    override fun findAll(): ApiResponse<Notices> {
        return ApiResponse.success(noticeQueryService.findAll())
    }

    @GetMapping("/{noticeId}")
    @ResponseStatus(HttpStatus.OK)
    override fun findById(
        @PathVariable("noticeId") noticeId: Long,
    ): ApiResponse<NoticeDto> {
        return ApiResponse.success(noticeQueryService.findById(noticeId))
    }

    @PatchMapping("/{noticeId}")
    @ResponseStatus(HttpStatus.OK)
    override fun update(
        @PathVariable("noticeId") noticeId: Long,
        @RequestBody command: UpdateNoticeCommand,
    ) {
        noticeCommandService.update(noticeId, command)
    }

    @DeleteMapping("/{noticeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun deleteById(
        @PathVariable("noticeId") noticeId: Long,
    ) {
        noticeCommandService.delete(noticeId)
    }
}
