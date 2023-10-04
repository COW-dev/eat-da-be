package com.mjucow.eatda.domain.store.service.command

import autoparams.kotlin.AutoKotlinSource
import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.store.entity.Store
import com.mjucow.eatda.domain.store.entity.objectmother.StoreMother
import com.mjucow.eatda.domain.store.service.command.dto.StoreCreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.StoreUpdateCommand
import com.mjucow.eatda.persistence.store.StoreRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull

@Import(value = [StoreCommandService::class])
class StoreCommandServiceDataTest : AbstractDataTest() {
    @Autowired
    lateinit var storeCommandService: StoreCommandService

    @Autowired
    lateinit var repository: StoreRepository

    @DisplayName("새로운 가게의 이름이 중복된다면 예외를 던진다")
    @Test
    fun throwExceptionWhenNewStoreDuplicatedName() {
        // given
        val store = StoreMother.create()
        repository.save(store)

        val command = StoreCreateCommand(name = store.name, address = store.address)

        // when
        val throwable = catchThrowable { storeCommandService.create(command) }

        // then
        assertThat(throwable).isNotNull()
    }

    @DisplayName("새로운 가게의 이름이 중복된다면 예외를 던진다")
    @Test
    fun createInstanceValidCommand() {
        // given
        val command = StoreCreateCommand(name = StoreMother.NAME, address = StoreMother.ADDRESS)

        // when
        val id = storeCommandService.create(command)

        // then
        assertThat(id).isNotNull()
    }

    @DisplayName("새로운 가게의 이름이 중복된다면 예외를 던진다: 필드 다 채움")
    @Test
    fun createInstanceValidFillCommand() {
        // given
        val command = StoreCreateCommand(
            name = StoreMother.NAME,
            address = StoreMother.ADDRESS,
            displayName = StoreMother.DISPLAY_NAME,
            phoneNumber = StoreMother.PHONE_NUMBER,
            imageAddress = StoreMother.IMAGE_ADDRESS,
            location = StoreMother.LOCATION
        )

        // when
        val id = storeCommandService.create(command)

        // then
        assertThat(id).isNotNull()
    }

    @DisplayName("삭제하려는 대상이 없어도 예외를 던지지 않는다")
    @ParameterizedTest
    @AutoKotlinSource
    fun notThrowExceptionWhenNotFoundEntity(id: Long) {
        // given

        // when
        val throwable = catchThrowable { storeCommandService.delete(id) }

        // then
        assertThat(throwable).isNull()
    }

    @DisplayName("삭제하려는 대상을 삭제한다")
    @Test
    fun delete() {
        // given
        val store = StoreMother.create()
        repository.save(store)

        // when
        storeCommandService.delete(store.id)

        // then
        assertThat(repository.existsById(store.id)).isFalse()
    }

    @DisplayName("수정하려는 가게 이름이 중복된다면 예외를 던진다")
    @Test
    fun throwExceptionWhenUpdateNameDuplicated() {
        // given
        val store1 = StoreMother.create()
        val store2 = Store(name = StoreMother.NAME + "1", address = StoreMother.ADDRESS)
        repository.save(store1)
        repository.save(store2)

        val command = StoreUpdateCommand(
            name = store1.name,
            address = store2.address,
            displayName = null,
            phoneNumber = null,
            imageAddress = null,
            location = null
        )

        // when
        val throwable = catchThrowable { storeCommandService.update(store2.id, command) }

        // then
        assertThat(throwable).isNotNull()
    }

    @DisplayName("가게 정보가 수정된다")
    @Test
    fun updateStore() {
        // given
        val store = StoreMother.create()
        repository.save(store)

        val command = StoreUpdateCommand(
            name = store.name,
            address = store.address,
            displayName = StoreMother.DISPLAY_NAME,
            phoneNumber = StoreMother.PHONE_NUMBER,
            imageAddress = StoreMother.IMAGE_ADDRESS,
            location = StoreMother.LOCATION
        )

        // when
        storeCommandService.update(store.id, command)

        // then
        val updatedStore = repository.findByIdOrNull(store.id)!!
        assertAll(
            { assertThat(updatedStore.name).isEqualTo(command.name) },
            { assertThat(updatedStore.address).isEqualTo(command.address) },
            { assertThat(updatedStore.displayName).isEqualTo(command.displayName) },
            { assertThat(updatedStore.phoneNumber).isEqualTo(command.phoneNumber) },
            { assertThat(updatedStore.imageAddress).isEqualTo(command.imageAddress) },
            { assertThat(updatedStore.location).isEqualTo(command.location) }
        )
    }
}
