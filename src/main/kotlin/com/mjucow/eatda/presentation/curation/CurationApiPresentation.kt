package com.mjucow.eatda.presentation.curation

import com.mjucow.eatda.common.dto.CursorPage
import com.mjucow.eatda.domain.curation.service.command.dto.AddStoreCommand
import com.mjucow.eatda.domain.curation.service.command.dto.CreateCurationCommand
import com.mjucow.eatda.domain.curation.service.command.dto.UpdateCurationCommand
import com.mjucow.eatda.domain.curation.service.query.dto.Curations
import com.mjucow.eatda.domain.store.service.query.dto.StoreDto
import com.mjucow.eatda.presentation.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "큐레이션 API", description = "큐레이션을 관리해주는 API")
interface CurationApiPresentation {
    @Operation(summary = "큐레이션 생성", description = "큐레이션를 생성합니다.")
    fun create(command: CreateCurationCommand): ApiResponse<Long>

    @Operation(summary = "큐레이션 전체 조회", description = "모든 큐레이션를 조회합니다.")
    fun findAll(): ApiResponse<Curations>

    @Operation(summary = "큐레이션별 가게 전체 조회", description = "특정 큐레이션에 있는 모든 가게를 조회합니다.")
    fun findAllStoresByCurationId(
        @PathVariable("curationId") id: Long,
        @RequestParam("cursor", required = false) cursor: Long?,
        @RequestParam("pageSize", required = false, defaultValue = "20") pageSize: Int,
    ): ApiResponse<CursorPage<StoreDto>>

    @Operation(summary = "큐레이션 수정", description = "큐레이션의 내용을 수정합니다.")
    @Parameter(name = "curationId", description = "수정할 큐레이션의 ID")
    fun update(id: Long, command: UpdateCurationCommand)

    @Operation(summary = "큐레이션 삭제", description = "큐레이션을 삭제합니다.")
    @Parameter(name = "curationId", description = "삭제할 큐레이션의 ID")
    fun delete(id: Long)

    @Operation(summary = "큐레이션 가게 추가", description = "큐레이션에 가게를 추가합니다.")
    @Parameter(name = "curationId", description = "큐레이션의 ID")
    fun addStore(id: Long, command: AddStoreCommand): ApiResponse<Long>
}
