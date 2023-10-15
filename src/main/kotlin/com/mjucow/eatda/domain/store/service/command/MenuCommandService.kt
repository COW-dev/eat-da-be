package com.mjucow.eatda.domain.store.service.command

import com.mjucow.eatda.domain.store.entity.Menu
import com.mjucow.eatda.domain.store.service.command.dto.MenuCreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.MenuUpdateCommand
import com.mjucow.eatda.persistence.store.MenuRepository
import com.mjucow.eatda.persistence.store.StoreRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MenuCommandService(
    private val repository: MenuRepository,
    private val storeRepository: StoreRepository,
) {
    fun create(storeId: Long, command: MenuCreateCommand): Long {
        val store = storeRepository.getReferenceById(storeId)

        val menu = Menu(
            name = command.name,
            price = command.price,
            imageAddress = command.imageAddress,
            store = store
        )
        return repository.save(menu).id
    }

    fun update(id: Long, command: MenuUpdateCommand) {
        val menu = repository.getReferenceById(id).apply {
            name = command.name
            price = command.price
            imageAddress = command.imageAddress
        }
        repository.save(menu)
    }

    fun delete(id: Long) {
        repository.deleteById(id)
    }
}
