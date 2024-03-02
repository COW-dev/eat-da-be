package com.mjucow.eatda.domain.curation.entity.objectmother

import com.mjucow.eatda.common.objectmother.EntityMother
import com.mjucow.eatda.domain.curation.entity.Curation

object CurationMother : EntityMother<Curation>() {
    const val TITLE = "명지대 점심 특선"
    const val DESCRIPTION = "점심 특선 메뉴를 판매하는 음식점들이에요."
    const val IMAGE_ADDRESS = "store/232D8241-C6A9-4AD9-B0EA-56F6DD24BADF.png"

    override fun createFillInstance() = Curation(TITLE, DESCRIPTION, IMAGE_ADDRESS)

    override fun createDefaultInstance() = Curation(TITLE, DESCRIPTION, IMAGE_ADDRESS)
}
