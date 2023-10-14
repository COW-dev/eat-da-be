package com.mjucow.eatda.domain.store.service.command

import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.store.entity.objectmother.MenuMother
import com.mjucow.eatda.domain.store.entity.objectmother.StoreMother
import com.mjucow.eatda.domain.store.service.command.dto.MenuCreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.MenuUpdateCommand
import com.mjucow.eatda.persistence.store.MenuRepository
import com.mjucow.eatda.persistence.store.StoreRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull

private const val i = MenuMother.PRICE * 2

@Import(value = [MenuCommandService::class])
class MenuCommandServiceTest : AbstractDataTest() {
    @Autowired
    lateinit var commandService: MenuCommandService

    @Autowired
    lateinit var repository: MenuRepository

    @Autowired
    lateinit var storeRepository: StoreRepository

    @BeforeEach
    fun setUp() {
        MenuMother.store = storeRepository.save(StoreMother.create { it.name = "default" })
    }

    @DisplayName("storeId에 해당하는 store가 없다면 에외를 던진다")
    @Test
    fun test1() {
        // given
        val command = MenuCreateCommand(
            storeId = Long.MAX_VALUE,
            name = MenuMother.NAME,
            price = MenuMother.PRICE,
        )

        // when
        val throwable = catchThrowable { commandService.create(command) }

        // then
        assertThat(throwable).isNotNull()
    }

    @DisplayName("정상적인 값이 입력되면 메뉴가 생성된다")
    @Test
    fun test2() {
        // given
        val store = storeRepository.save(StoreMother.create())
        val command = MenuCreateCommand(
            storeId = store.id,
            name = MenuMother.NAME,
            price = MenuMother.PRICE,
        )

        // when
        val menuId = commandService.create(command)

        // then
        assertThat(repository.getReferenceById(menuId)).isNotNull()
    }

    @DisplayName("id에 해당하는 menu가 없다면 에외를 던진다")
    @Test
    fun test3() {
        // given
        val command = MenuUpdateCommand(
            id = 1L,
            name = MenuMother.NAME,
            price = MenuMother.PRICE,
        )

        // when
        val throwable = catchThrowable { commandService.update(command) }

        // then
        assertThat(throwable).isNotNull()
    }

    @DisplayName("정상적인 값이 들어오면 메뉴의 정보가 변경 된다")
    @Test
    fun test4() {
        // given
        val menu = repository.save(MenuMother.create())
        val updatedName = "updateName"
        val updatedPrice = MenuMother.PRICE * 2
        val command = MenuUpdateCommand(
            id = menu.id,
            name = updatedName,
            price = updatedPrice,
        )

        // when
        commandService.update(command)

        // then
        val updatedMenu = repository.getReferenceById(menu.id)
        assertAll(
            { assertThat(updatedMenu.name).isEqualTo(updatedName) },
            { assertThat(updatedMenu.price).isEqualTo(updatedPrice) },
        )
    }

    @DisplayName("데이터가 없어도 삭제 동작은 정상 처리된다")
    @Test
    fun test5() {
        // given
        val menuId = Long.MAX_VALUE;

        // when
        commandService.delete(menuId)

        // then
        assertThat(repository.findByIdOrNull(menuId)).isNull()
    }

    @DisplayName("삭제할 대상을 삭제한다 정상 처리된다")
    @Test
    fun test6() {
        // given
        val menu = repository.save(MenuMother.create())

        // when
        commandService.delete(menu.id)

        // then
        assertThat(repository.findByIdOrNull(menu.id)).isNull()
    }
}

