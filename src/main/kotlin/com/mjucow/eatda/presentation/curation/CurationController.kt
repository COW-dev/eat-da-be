package com.mjucow.eatda.presentation.curation

import com.mjucow.eatda.common.dto.CursorPage
import com.mjucow.eatda.domain.curation.service.command.CurationCommandService
import com.mjucow.eatda.domain.curation.service.command.dto.AddStoreCommand
import com.mjucow.eatda.domain.curation.service.command.dto.CreateCurationCommand
import com.mjucow.eatda.domain.curation.service.command.dto.UpdateCurationCommand
import com.mjucow.eatda.domain.curation.service.query.CurationQueryService
import com.mjucow.eatda.domain.curation.service.query.dto.Curations
import com.mjucow.eatda.domain.store.service.query.StoreQueryService
import com.mjucow.eatda.domain.store.service.query.dto.StoreDto
import com.mjucow.eatda.presentation.common.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import kotlin.math.min

@RestController
@RequestMapping("/api/v1/curations")
class CurationController(
    private val storeQueryService: StoreQueryService,
    private val curationQueryService: CurationQueryService,
    private val curationCommandService: CurationCommandService,
) : CurationApiPresentation {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    override fun create(@RequestBody command: CreateCurationCommand): ApiResponse<Long> {
        return ApiResponse.success(curationCommandService.create(command))
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    override fun findAll(): ApiResponse<Curations> {
        return ApiResponse.success(curationQueryService.findAll())
    }

    @GetMapping("/{curationId}/stores")
    @ResponseStatus(HttpStatus.OK)
    override fun findAllStoresByCurationId(
        @PathVariable("curationId", required = false) id: Long,
        @RequestParam("cursor", required = false) cursor: Long?,
        @RequestParam("pageSize", required = false, defaultValue = "20") pageSize: Int,
    ): ApiResponse<CursorPage<StoreDto>> {
        val results = storeQueryService.findAllByCurationAndCursor(cursor, id, pageSize)

        val contents = results.subList(0, min(pageSize, results.size))
        val hasNext = results.size > pageSize
        val nextCursor = if (hasNext) contents.last().id.toString() else null

        return ApiResponse.success(CursorPage(contents, hasNext, nextCursor))
    }

    @PatchMapping("/{curationId}")
    @ResponseStatus(HttpStatus.OK)
    override fun update(
        @PathVariable("curationId") id: Long,
        @RequestBody command: UpdateCurationCommand,
    ) {
        return curationCommandService.update(id, command)
    }

    @DeleteMapping("/{curationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun delete(@PathVariable("curationId") id: Long) {
        return curationCommandService.delete(id)
    }

    @PostMapping("/{curationId}/store")
    @ResponseStatus(HttpStatus.CREATED)
    override fun addStore(
        @PathVariable("curationId") id: Long,
        @RequestBody command: AddStoreCommand,
    ): ApiResponse<Long> {
        return ApiResponse.success(curationCommandService.addStore(id, command))
    }
}
