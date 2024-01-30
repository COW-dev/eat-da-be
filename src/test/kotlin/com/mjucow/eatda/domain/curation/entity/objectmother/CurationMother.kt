package com.mjucow.eatda.domain.curation.entity.objectmother

import com.mjucow.eatda.common.objectmother.EntityMother
import com.mjucow.eatda.domain.curation.entity.Curation

object CurationMother : EntityMother<Curation>() {
    const val TITLE = "명지대 점심 특선"
    const val DESCRIPTION = "점심 특선 메뉴를 판매하는 음식점들이에요."

    override fun createFillInstance() = Curation(TITLE, DESCRIPTION)

    override fun createDefaultInstance() = Curation(TITLE, DESCRIPTION)
}
