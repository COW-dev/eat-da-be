package com.mjucow.eatda.domain.curation.service.query.dto

import com.mjucow.eatda.domain.curation.entity.Curation
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant

data class CurationDto(
    @Schema(name = "id", example = "1")
    val id: Long,
    @Schema(name = "title", example = "큐레이션 제목")
    val title: String,
    @Schema(name = "description", example = "큐레이션 설명")
    val description: String,
    @Schema(name = "createdAt", example = "2023-11-20T14:11:53.025126Z")
    val createdAt: Instant,
) {

    companion object {
        fun from(domain: Curation): CurationDto {
            return CurationDto(
                domain.id,
                domain.title,
                domain.description,
                domain.createdAt
            )
        }
    }
}
