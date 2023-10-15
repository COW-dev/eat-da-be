package com.mjucow.eatda.domain.store.service.command

import com.mjucow.eatda.domain.store.entity.Store
import com.mjucow.eatda.domain.store.service.command.dto.StoreCreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.StoreUpdateCommand
import com.mjucow.eatda.persistence.store.StoreRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StoreCommandService(
    val repository: StoreRepository,
) {
    fun create(command: StoreCreateCommand): Long {
        checkDuplicateName(command.name)
        val store = Store(
            name = command.name,
            address = command.address,
            displayName = command.displayName,
            phoneNumber = command.phoneNumber,
            imageAddress = command.imageAddress,
            location = command.location
        )
        return repository.save(store).id
    }

    private fun checkDuplicateName(name: String) {
        require(repository.existsByName(name).not())
    }

    fun delete(id: Long) {
        repository.deleteById(id)
    }

    fun update(id: Long, command: StoreUpdateCommand) {
        val store = repository.getReferenceById(id)
        if (store.name != command.name) {
            checkDuplicateName(command.name)
        }

        store.name = command.name
        store.address = command.address
        store.displayName = command.displayName
        store.phoneNumber = command.phoneNumber
        store.imageAddress = command.imageAddress
        store.location = command.location

        repository.save(store)
    }
}
