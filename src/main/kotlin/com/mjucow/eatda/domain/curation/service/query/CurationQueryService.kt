package com.mjucow.eatda.domain.curation.service.query

import com.mjucow.eatda.domain.curation.service.query.dto.CurationDto
import com.mjucow.eatda.domain.curation.service.query.dto.Curations
import com.mjucow.eatda.persistence.curation.CurationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CurationQueryService(
    private val repository: CurationRepository,
) {
    fun findAll(): Curations {
        return Curations(repository.findAll().map(CurationDto::from))
    }
}
