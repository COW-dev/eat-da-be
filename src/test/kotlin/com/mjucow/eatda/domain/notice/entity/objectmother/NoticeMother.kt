package com.mjucow.eatda.domain.notice.entity.objectmother

import com.mjucow.eatda.common.objectmother.EntityMother
import com.mjucow.eatda.domain.notice.entity.Notice

object NoticeMother : EntityMother<Notice>() {
    val title = "공지사항 제목"
    val content = "공지사항 내용"
    override fun createFillInstance() = Notice(title, content)

    override fun createDefaultInstance() = Notice(title, content)
}
