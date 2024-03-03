package com.mjucow.eatda.domain.curation.service.command

import autoparams.kotlin.AutoKotlinSource
import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.curation.entity.objectmother.CurationMother
import com.mjucow.eatda.domain.curation.service.command.dto.AddStoreCommand
import com.mjucow.eatda.domain.curation.service.command.dto.CreateCurationCommand
import com.mjucow.eatda.domain.curation.service.command.dto.UpdateCurationCommand
import com.mjucow.eatda.domain.store.entity.objectmother.StoreMother
import com.mjucow.eatda.persistence.curation.CurationRepository
import com.mjucow.eatda.persistence.store.StoreRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(CurationCommandService::class)
class CurationCommandServiceTest : AbstractDataTest() {
    @Autowired
    lateinit var curationCommandService: CurationCommandService

    @Autowired
    lateinit var repository: CurationRepository

    @Autowired
    lateinit var storeRepository: StoreRepository

    @DisplayName("정상적인 값이 들어오면 큐레이션이 생성된다")
    @Test
    fun task1() {
        // given
        val command = CreateCurationCommand(
            title = CurationMother.TITLE,
            description = CurationMother.DESCRIPTION,
            imageAddress = CurationMother.IMAGE_ADDRESS
        )

        // when
        val id = curationCommandService.create(command)

        // then
        Assertions.assertThat(repository.getReferenceById(id)).isNotNull()
    }

    @DisplayName("정상적인 값이 들어오면 큐레이션 정보가 수정된다")
    @Test
    fun task2() {
        // given
        val curation = repository.save(CurationMother.create())
        val updatedTitle = CurationMother.TITLE + "x"
        val updatedDescription = CurationMother.DESCRIPTION + "x"
        val updatedImageAddress = CurationMother.IMAGE_ADDRESS + "x"
        val command = UpdateCurationCommand(
            title = updatedTitle,
            description = updatedDescription,
            imageAddress = updatedImageAddress
        )

        // when
        curationCommandService.update(curation.id, command)

        // then
        val updatedCuration = repository.getReferenceById(curation.id)
        assertAll(
            { Assertions.assertThat(updatedCuration.title).isEqualTo(updatedTitle) },
            { Assertions.assertThat(updatedCuration.description).isEqualTo(updatedDescription) }
        )
    }

    @DisplayName("삭제하려는 대상이 없어도 예외를 던지지 않는다")
    @ParameterizedTest
    @AutoKotlinSource
    fun task3(id: Long) {
        // given

        // when
        val throwable = Assertions.catchThrowable { curationCommandService.delete(id) }

        // then
        Assertions.assertThat(throwable).isNull()
    }

    @DisplayName("삭제하려는 대상을 삭제한다")
    @Test
    fun task4() {
        // given
        val curation = CurationMother.create()
        repository.save(curation)

        // when
        curationCommandService.delete(curation.id)

        // then
        Assertions.assertThat(repository.existsById(curation.id)).isFalse()
    }

    @DisplayName("id에 해당하는 큐레이션이 없다면 예외를 던진다")
    @ParameterizedTest
    @AutoKotlinSource
    fun test5(id: Long) {
        // given
        val storeId = storeRepository.save(StoreMother.create()).id
        val command = AddStoreCommand(storeId)

        // when
        val throwable = Assertions.catchThrowable { curationCommandService.addStore(id, command) }

        // then
        Assertions.assertThat(throwable).isNotNull()
    }

    @DisplayName("storeId에 해당하는 가게가 없다면 예외를 던진다")
    @ParameterizedTest
    @AutoKotlinSource
    fun test6(storeId: Long) {
        // given
        val id = curationCommandService.create(
            CreateCurationCommand(
                title = CurationMother.TITLE,
                description = CurationMother.DESCRIPTION,
                imageAddress = CurationMother.IMAGE_ADDRESS
            )
        )
        val command = AddStoreCommand(storeId)

        // when
        val throwable = Assertions.catchThrowable { curationCommandService.addStore(id, command) }

        // then
        Assertions.assertThat(throwable).isNotNull()
    }

    @DisplayName("가게가 추가된다")
    @Test
    fun test7() {
        // given
        val id = curationCommandService.create(
            CreateCurationCommand(
                title = CurationMother.TITLE,
                description = CurationMother.DESCRIPTION,
                imageAddress = CurationMother.IMAGE_ADDRESS
            )
        )
        val storeId = storeRepository.save(StoreMother.create()).id
        val command = AddStoreCommand(storeId)
        curationCommandService.addStore(id, command)

        // when
        val curation = repository.getReferenceById(id)

        // then
        Assertions.assertThat(curation.getStores().size).isEqualTo(1)
    }
}
