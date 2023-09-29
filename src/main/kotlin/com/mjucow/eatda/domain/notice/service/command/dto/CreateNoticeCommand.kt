package com.mjucow.eatda.domain.notice.service.command.dto

import jakarta.validation.constraints.NotBlank

data class CreateNoticeCommand(
    @field:NotBlank(message = "공지사항 제목은 필수 입력 값입니다.")
    val title: String,
    @field:NotBlank(message = "공지사항 내용은 필수 입력 값입니다.")
    val content: String,
)
