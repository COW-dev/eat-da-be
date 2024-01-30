package com.mjucow.eatda.domain.curation.service.query

import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.curation.entity.objectmother.CurationMother
import com.mjucow.eatda.persistence.curation.CurationRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(CurationQueryService::class)
class CurationQueryServiceTest : AbstractDataTest() {
    @Autowired
    lateinit var curationQueryService: CurationQueryService

    @Autowired
    lateinit var repository: CurationRepository

    @DisplayName("큐레이션이 없을 경우 빈 배열을 반환한다")
    @Test
    fun task1() {
        // given

        // when
        val curations = curationQueryService.findAll()

        // then
        Assertions.assertThat(curations).isEmpty()
    }

    @DisplayName("전체 큐레이션을 반환한다")
    @Test
    fun returnCategories() {
        // given
        repository.save(CurationMother.create())

        // when
        val curations = curationQueryService.findAll()

        // then
        Assertions.assertThat(curations).isNotEmpty
    }
}
