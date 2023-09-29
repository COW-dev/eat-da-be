package com.mjucow.eatda.domain.store.service.command

import autoparams.kotlin.AutoKotlinSource
import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.store.entity.Category
import com.mjucow.eatda.domain.store.service.command.dto.CreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.UpdateNameCommand
import com.mjucow.eatda.persistence.store.CategoryRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull

@Import(CategoryCommandService::class)
class CategoryCommandServiceDataTest: AbstractDataTest() {

    @Autowired
    lateinit var categoryCommandService: CategoryCommandService

    @Autowired
    lateinit var repository: CategoryRepository

    @DisplayName("이름이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun throwExceptionWhenNameIsEmpty(name: String) {
        // given
        val command = CreateCommand(name)

        // when
        val throwable = catchThrowable { categoryCommandService.create(command) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNameLengthGreaterThanMaxLength() {
        // given
        val name = "x".repeat(Category.MAX_NAME_LENGTH + 1)
        val command = CreateCommand(name)

        // when
        val throwable = catchThrowable { categoryCommandService.create(command) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("정상 입력이라면 객체가 생성된다")
    @Test
    fun createCategoryWhenValidInput() {
        // given
        val name = "validName"
        val command = CreateCommand(name)

        // when
        val domain = categoryCommandService.create(command)

        // then
        assertThat(domain).isNotNull
    }

    @DisplayName("새로운 이름이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun throwExceptionWhenNewNameIsEmpty(newName: String) {
        // given
        val id = 1L
        val command = UpdateNameCommand(newName)

        // when
        val throwable = catchThrowable { categoryCommandService.updateName(id, command) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNewNameLengthGreaterThanMaxLength() {
        // given
        val id = 1L
        val newName = "x".repeat(Category.MAX_NAME_LENGTH + 1)
        val command = UpdateNameCommand(newName)

        // when
        val throwable = catchThrowable { categoryCommandService.updateName(id, command) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("이름을 수정하려는 대상의 조회되지 않을 경우 예외를 던진다")
    @Test
    fun throwExceptionWhenNotFoundTarget() {
        // given
        val id = 1L
        val name = "validName"
        val command = UpdateNameCommand(name)

        // when
        val throwable = catchThrowable { categoryCommandService.updateName(id, command) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("변경하려는 이름이 기존과 같을 경우 변경 성공 처리한다")
    @Test
    fun updateNameWhenSameNameUpdate() {
        // given
        val prevName = "validName"
        val id = repository.save(Category(prevName)).id
        val newName = prevName
        val command = UpdateNameCommand(newName)

        // when
        categoryCommandService.updateName(id, command)

        // then
        assertThat(repository.getReferenceById(id).name).isEqualTo(newName)
    }

    @DisplayName("정상적인 이름이라면 변경 성공 처리한다")
    @Test
    fun updateNameWhenValidNameUpdate() {
        // given
        val prevName = "validName"
        val id = repository.save(Category(prevName)).id
        val newName = "valid"
        val command = UpdateNameCommand(newName)

        // when
        categoryCommandService.updateName(id, command)

        // then
        assertThat(repository.getReferenceById(id).name).isEqualTo(newName)
    }

    @DisplayName("저장된 데이터가 없어도 삭제 된다")
    @ParameterizedTest
    @AutoKotlinSource
    fun deleteWhenNotFound(id: Long) {
        // given

        // when
        categoryCommandService.delete(id)

        // then
        assertThat(repository.findByIdOrNull(id)).isNull()
    }

    @DisplayName("저장된 데이터를 삭제 된다")
    @Test
    fun deleteWhenNotFound() {
        // given
        val prevName = "validName"
        val id = repository.save(Category(prevName)).id

        // when
        categoryCommandService.delete(id)

        // then
        assertThat(repository.findByIdOrNull(id)).isNull()
    }
}
