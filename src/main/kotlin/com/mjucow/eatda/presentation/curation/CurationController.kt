package com.mjucow.eatda.presentation.curation

import com.mjucow.eatda.domain.curation.service.command.CurationCommandService
import com.mjucow.eatda.domain.curation.service.command.dto.CreateCurationCommand
import com.mjucow.eatda.domain.curation.service.command.dto.UpdateCurationCommand
import com.mjucow.eatda.domain.curation.service.query.CurationQueryService
import com.mjucow.eatda.domain.curation.service.query.dto.Curations
import com.mjucow.eatda.presentation.common.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/curations")
@RestController
class CurationController(
    private val curationQueryService: CurationQueryService,
    private val curationCommandService: CurationCommandService,
) : CurationApiPresentation {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    override fun create(command: CreateCurationCommand): ApiResponse<Long> {
        return ApiResponse.success(curationCommandService.create(command))
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    override fun findAll(): ApiResponse<Curations> {
        return ApiResponse.success(curationQueryService.findAll())
    }

    @PatchMapping("/{curationId}")
    @ResponseStatus(HttpStatus.OK)
    override fun update(
        @PathVariable("curationId") id: Long,
        command: UpdateCurationCommand,
    ) {
        return curationCommandService.update(id, command)
    }

    @DeleteMapping("/{curationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun delete(@PathVariable("curationId") id: Long) {
        return curationCommandService.delete(id)
    }
}
