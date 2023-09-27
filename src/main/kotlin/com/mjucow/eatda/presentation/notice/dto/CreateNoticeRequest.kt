package com.mjucow.eatda.presentation.notice.dto

import jakarta.validation.constraints.NotBlank

data class CreateNoticeRequest(
    @field:NotBlank(message = "공지사항 제목은 필수 입력 값입니다.")
    val title: String,
    @field:NotBlank(message = "공지사항 내용은 필수 입력 값입니다.")
    val content: String,
)
