package com.mjucow.eatda.domain.notice.service.query.dto

data class Notices(
    val noticeList: List<NoticeDto>,
) : ArrayList<NoticeDto>(noticeList)
