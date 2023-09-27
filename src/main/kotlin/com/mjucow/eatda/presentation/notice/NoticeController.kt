package com.mjucow.eatda.presentation.notice

import com.mjucow.eatda.application.notice.NoticeService
import com.mjucow.eatda.presentation.common.ApiResponse
import com.mjucow.eatda.presentation.notice.dto.CreateNoticeRequest
import com.mjucow.eatda.presentation.notice.dto.NoticeIdResponse
import com.mjucow.eatda.presentation.notice.dto.NoticeResponse
import com.mjucow.eatda.presentation.notice.dto.UpdateNoticeRequest
import jakarta.validation.Valid
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
class NoticeController(private val noticeService: NoticeService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody @Valid
        request: CreateNoticeRequest,
    ): ApiResponse<NoticeIdResponse> {
        return ApiResponse.success(noticeService.create(request))
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): ApiResponse<List<NoticeResponse>> {
        return ApiResponse.success(noticeService.findAll())
    }

    @GetMapping("/{noticeId}")
    @ResponseStatus(HttpStatus.OK)
    fun findById(
        @PathVariable noticeId: Long,
    ): ApiResponse<NoticeResponse> {
        return ApiResponse.success(noticeService.findById(noticeId))
    }

    @PatchMapping("/{noticeId}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @PathVariable noticeId: Long,
        @RequestBody request: UpdateNoticeRequest,
    ): ApiResponse<NoticeIdResponse> {
        return ApiResponse.success(noticeService.update(noticeId, request))
    }

    @DeleteMapping("/{noticeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(
        @PathVariable noticeId: Long,
    ) {
        return noticeService.deleteById(noticeId)
    }
}
