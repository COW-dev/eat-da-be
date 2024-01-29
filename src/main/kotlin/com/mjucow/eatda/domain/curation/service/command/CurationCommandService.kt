package com.mjucow.eatda.domain.curation.service.command

import com.mjucow.eatda.domain.curation.entity.Curation
import com.mjucow.eatda.domain.curation.service.command.dto.CreateCurationCommand
import com.mjucow.eatda.domain.curation.service.command.dto.UpdateCurationCommand
import com.mjucow.eatda.persistence.curation.CurationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CurationCommandService(
    private val repository: CurationRepository,
) {
    fun create(command: CreateCurationCommand): Long {
        return repository.save(Curation(command.title, command.description)).id
    }

    fun update(id: Long, command: UpdateCurationCommand) {
        val (newTitle, newDescription) = command
        val updatedCuration = repository.getReferenceById(id).apply {
            title = newTitle
            description = newDescription
        }

        repository.save(updatedCuration)
    }

    fun delete(id: Long) {
        val curation = repository.findByIdOrNull(id) ?: return

        repository.delete(curation)
    }
}
