package com.mjucow.eatda.presentation.notice.dto

import com.mjucow.eatda.domain.notice.Notice

data class NoticeIdResponse(
    val id: Long,
) {
    constructor(notice: Notice) : this(
        notice.id!!
    )
}
