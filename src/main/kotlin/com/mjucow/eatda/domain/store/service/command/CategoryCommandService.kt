package com.mjucow.eatda.domain.store.service.command

import com.mjucow.eatda.domain.store.entity.Category
import com.mjucow.eatda.domain.store.service.command.dto.CreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.UpdateNameCommand
import com.mjucow.eatda.persistence.store.CategoryRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class CategoryCommandService(
    private val repository: CategoryRepository,
) {
    fun create(command: CreateCommand): Long {
        val name = command.name.trim()
        checkDuplicatedName(name)

        return repository.save(Category(name)).id
    }

    fun updateName(id: Long, command: UpdateNameCommand) {
        val domain = getById(id)

        val newName = command.name.trim()
        if (domain.name == newName) {
            return
        }
        checkDuplicatedName(newName)
        domain.name = newName

        repository.save(domain)
    }

    fun delete(id: Long) {
        repository.deleteById(id)
    }

    private fun getById(id: Long): Category {
        return repository.findByIdOrNull(id) ?: throw IllegalArgumentException()
    }
    private fun checkDuplicatedName(name: String) {
        require(repository.existsByName(name).not())
    }
}
