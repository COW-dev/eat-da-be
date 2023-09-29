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
        val newName = command.name.trim()
        require(repository.existsByName(newName).not())

        return repository.save(Category(newName)).id
    }

    fun updateName(id: Long, command: UpdateNameCommand) {
        val domain = getById(id)

        val newName = command.name.trim()
        if (domain.name == newName) {
            return
        }
        domain.name = newName

        repository.save(domain)
    }

    fun delete(id: Long) {
        val domain = repository.findByIdOrNull(id) ?: return
        repository.delete(domain)
    }

    private fun getById(id: Long): Category {
        return repository.findByIdOrNull(id) ?: throw IllegalArgumentException()
    }
}
