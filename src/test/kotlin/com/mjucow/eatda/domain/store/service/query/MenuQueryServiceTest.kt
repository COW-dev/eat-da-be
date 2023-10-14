package com.mjucow.eatda.domain.store.service.query

import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.store.entity.Store
import com.mjucow.eatda.domain.store.entity.objectmother.MenuMother
import com.mjucow.eatda.domain.store.entity.objectmother.StoreMother
import com.mjucow.eatda.persistence.store.MenuRepository
import com.mjucow.eatda.persistence.store.StoreRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(value = [MenuQueryService::class])
class MenuQueryServiceTest : AbstractDataTest() {
    @Autowired
    lateinit var queryService: MenuQueryService

    @Autowired
    lateinit var repository: MenuRepository

    @Autowired
    lateinit var storeRepository: StoreRepository

    lateinit var store: Store

    @BeforeEach
    fun setUp() {
        store = storeRepository.save(StoreMother.create { it.name = "default" })
        MenuMother.store = store
    }

    @DisplayName("조회할 메뉴가 없으면 에외를 던진다")
    @Test
    fun test1() {
        // given

        // when
        val throwable = catchThrowable { queryService.findById(Long.MAX_VALUE) }

        // then
        assertThat(throwable).isNotNull()
    }

    @DisplayName("조회할 메뉴가 없으면 조회 결과를 반환환다")
    @Test
    fun test2() {
        // given
        val menu = repository.save(MenuMother.create())

        // when
        val dto = queryService.findById(menu.id)

        // then
        assertThat(dto.id).isEqualTo(menu.id)
    }

    @DisplayName("조회할 메뉴가 없으면 빈 리스트가 반환된다")
    @Test
    fun test3() {
        // given

        // when
        val menuList = queryService.findAll(Long.MAX_VALUE)

        // then
        assertThat(menuList.menu).isEmpty()
    }

    @DisplayName("조회할 메뉴가 있으면 해당 메뉴들이 조회된다")
    @Test
    fun test4() {
        // given
        val targetMenuCount = 5
        repeat(targetMenuCount) { i ->
            repository.save(MenuMother.create { it.name = "name$i" })
        }

        // when
        val menuList = queryService.findAll(store.id)

        // then
        assertThat(menuList.menu.size).isEqualTo(targetMenuCount)
    }
}
