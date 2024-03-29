package com.mjucow.eatda.domain.curation.service.command

import com.mjucow.eatda.domain.curation.entity.Curation
import com.mjucow.eatda.domain.curation.service.command.dto.AddStoreCommand
import com.mjucow.eatda.domain.curation.service.command.dto.CreateCurationCommand
import com.mjucow.eatda.domain.curation.service.command.dto.UpdateCurationCommand
import com.mjucow.eatda.persistence.curation.CurationRepository
import com.mjucow.eatda.persistence.store.StoreRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CurationCommandService(
    private val repository: CurationRepository,
    private val storeRepository: StoreRepository,

) {
    fun create(command: CreateCurationCommand): Long {
        return repository.save(Curation(command.title, command.description, command.imageAddress)).id
    }

    fun update(id: Long, command: UpdateCurationCommand) {
        val (newTitle, newDescription, newImageAddress) = command
        val updatedCuration = repository.getReferenceById(id).apply {
            title = newTitle
            description = newDescription
            imageAddress = newImageAddress
        }

        repository.save(updatedCuration)
    }

    fun delete(id: Long) {
        val curation = repository.findByIdOrNull(id) ?: return

        repository.delete(curation)
    }

    fun addStore(id: Long, command: AddStoreCommand): Long {
        val (storeId) = command
        val curation = repository.getReferenceById(id)
        val store = storeRepository.getReferenceById(storeId)

        curation.addStore(store)

        return repository.save(curation).id
    }
}
