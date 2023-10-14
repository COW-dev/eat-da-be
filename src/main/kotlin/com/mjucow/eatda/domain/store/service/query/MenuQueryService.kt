package com.mjucow.eatda.domain.store.service.query

import com.mjucow.eatda.domain.store.service.query.dto.MenuList
import com.mjucow.eatda.persistence.store.MenuRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MenuQueryService(
    private val repository: MenuRepository,
) {
    fun findById(id: Long) : MenuDto {
        return MenuDto.from(repository.getReferenceById(id))
    }

    fun findAll(storeId: Long) : MenuList {
        return MenuList(repository.findAllByStoreId(storeId).map(MenuDto::from))
    }
}
