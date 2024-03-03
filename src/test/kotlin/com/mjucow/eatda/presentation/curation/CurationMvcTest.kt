package com.mjucow.eatda.presentation.curation

import autoparams.kotlin.AutoKotlinSource
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.mjucow.eatda.domain.curation.entity.objectmother.CurationMother
import com.mjucow.eatda.domain.curation.service.command.CurationCommandService
import com.mjucow.eatda.domain.curation.service.command.dto.AddStoreCommand
import com.mjucow.eatda.domain.curation.service.command.dto.CreateCurationCommand
import com.mjucow.eatda.domain.curation.service.command.dto.UpdateCurationCommand
import com.mjucow.eatda.domain.curation.service.query.CurationQueryService
import com.mjucow.eatda.domain.curation.service.query.dto.CurationDto
import com.mjucow.eatda.domain.curation.service.query.dto.Curations
import com.mjucow.eatda.domain.store.entity.objectmother.StoreMother
import com.mjucow.eatda.domain.store.service.query.StoreQueryService
import com.mjucow.eatda.domain.store.service.query.dto.StoreDto
import com.mjucow.eatda.presentation.AbstractMockMvcTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.core.Is
import org.hamcrest.core.IsNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.stream.IntStream

@WebMvcTest(CurationController::class)
class CurationMvcTest : AbstractMockMvcTest() {
    @MockkBean(relaxUnitFun = true)
    lateinit var curationQueryService: CurationQueryService

    @MockkBean(relaxUnitFun = true)
    lateinit var curationCommandService: CurationCommandService

    @MockkBean(relaxUnitFun = true)
    lateinit var storeQueryService: StoreQueryService

    @Test
    fun findAll() {
        // given
        val entityId = 1L
        val curations =
            Curations(listOf(CurationDto.from(CurationMother.createWithId(id = entityId))))
        every { curationQueryService.findAll() } returns curations

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(BASE_URI)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("message", Is.`is`(IsNull.nullValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("body").exists())
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "curation-findAll",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("curation")
                        .description("큐레이션 전체 조회")
                        .responseFields(
                            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING)
                                .description("에러 메세지"),
                            PayloadDocumentation.fieldWithPath("body").type(JsonFieldType.ARRAY)
                                .description("큐레이션 데이터"),
                            PayloadDocumentation.fieldWithPath("body[].id")
                                .type(JsonFieldType.NUMBER)
                                .description("큐레이션 식별자"),
                            PayloadDocumentation.fieldWithPath("body[].title")
                                .type(JsonFieldType.STRING)
                                .description("큐레이션 제목"),
                            PayloadDocumentation.fieldWithPath("body[].description")
                                .type(JsonFieldType.STRING)
                                .description("큐레이션 설명"),
                            PayloadDocumentation.fieldWithPath("body[].stores")
                                .type(JsonFieldType.ARRAY)
                                .description("큐레이션 가게들")
                        )
                )
            )
    }

    @Test
    fun findAllByCurationAndCursor_1() {
        // given
        val curationId = 1L
        val pageSize = 20
        val storeDtos = IntStream.range(0, 20).mapToObj {
            StoreDto(
                id = (it + 1).toLong(),
                displayedName = StoreMother.DISPLAY_NAME,
                address = StoreMother.ADDRESS,
                phoneNumber = null,
                imageAddress = null,
                location = null
            )
        }.toList()

        every { storeQueryService.findAllByCurationAndCursor(any(), any(), any()) } returns storeDtos

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(
                "$BASE_URI/{curationId}/stores?cursor={cursor}&pageSize={pageSize}",
                curationId,
                storeDtos.size + 1,
                pageSize
            )
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("message", Is.`is`(IsNull.nullValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("body").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("body.contents").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("body.hasNext").isBoolean())
            .andExpect(MockMvcResultMatchers.jsonPath("body.nextCursor", Is.`is`(IsNull.nullValue())))
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "store-findAllByCursor",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("store")
                        .description("커서 기반 큐레이션 가게 조회")
                        .queryParameters(
                            ResourceDocumentation.parameterWithName("categoryId").description("조회하는 가게의 큐레이션의 식별자").optional(),
                            ResourceDocumentation.parameterWithName("pageSize").description("조회할 페이지 사이즈").optional()
                        )
                        .pathParameters(
                            ResourceDocumentation.parameterWithName("cursor").description("조회한 마지막 가게 식별자").optional()
                        )
                        .responseFields(
                            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메세지"),
                            PayloadDocumentation.fieldWithPath("body").type(JsonFieldType.ARRAY).description("조회된 가게 리스트"),
                            PayloadDocumentation.fieldWithPath("body[0].id").type(JsonFieldType.NUMBER).description("가게 식별자"),
                            PayloadDocumentation.fieldWithPath("body[0].displayedName").type(JsonFieldType.STRING).description("보여지는 이름"),
                            PayloadDocumentation.fieldWithPath("body[0].address").type(JsonFieldType.STRING).description("가게 주소"),
                            PayloadDocumentation.fieldWithPath("body[0].phoneNumber").type(JsonFieldType.STRING).description("가게 연락처"),
                            PayloadDocumentation.fieldWithPath("body[0].imageAddress").type(JsonFieldType.STRING).description("가게 이미지 주소"),
                            PayloadDocumentation.fieldWithPath("body[0].location").type(JsonFieldType.OBJECT).description("가게 위치 정보"),
                            PayloadDocumentation.fieldWithPath("body[0].location.latitude").type(JsonFieldType.STRING).description("가게 위치 위도"),
                            PayloadDocumentation.fieldWithPath("body[0].location.longitude").type(JsonFieldType.STRING).description("가게 위치 경도")
                        )
                )
            )
    }

    @Test
    fun findAllByCurationAndCursor_2() {
        // given
        val curationId = 1L
        val pageSize = 20
        val storeDtos = IntStream.range(0, pageSize * 2).mapToObj {
            StoreDto(
                id = (it + 1).toLong(),
                displayedName = StoreMother.DISPLAY_NAME,
                address = StoreMother.ADDRESS,
                phoneNumber = null,
                imageAddress = null,
                location = null
            )
        }.toList()

        every { storeQueryService.findAllByCurationAndCursor(any(), any(), any()) } returns storeDtos

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(
                "$BASE_URI/{curationId}/stores?cursor={cursor}&pageSize={pageSize}",
                curationId,
                storeDtos.size + 1,
                pageSize
            )
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("message", Is.`is`(IsNull.nullValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("body").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("body.contents").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("body.hasNext").isBoolean())
            .andExpect(MockMvcResultMatchers.jsonPath("body.nextCursor").isString)
    }

    @ParameterizedTest
    @AutoKotlinSource
    fun create(id: Long) {
        // given
        val createCurationCommand = CreateCurationCommand(
            title = CurationMother.TITLE,
            description = CurationMother.DESCRIPTION,
            imageAddress = CurationMother.IMAGE_ADDRESS
        )
        val content = objectMapper.writeValueAsString(createCurationCommand)
        every { curationCommandService.create(any()) } returns id

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("body", Is.`is`(id)))
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "curation-create",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("curation")
                        .description("큐레이션 생성")
                        .requestFields(
                            PayloadDocumentation.fieldWithPath("title").type(JsonFieldType.STRING)
                                .description("큐레이션 제목"),
                            PayloadDocumentation.fieldWithPath("description")
                                .type(JsonFieldType.STRING)
                                .description("큐레이션 설명")
                        )
                        .responseFields(
                            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING)
                                .description("에러 메세지"),
                            PayloadDocumentation.fieldWithPath("body").type(JsonFieldType.NUMBER)
                                .description("생성된 큐레이션 식별자")
                        )
                )
            )
    }

    @Test
    fun update() {
        // given
        val id = 1L
        val updateCurationCommand = UpdateCurationCommand(
            title = CurationMother.TITLE,
            description = CurationMother.DESCRIPTION,
            imageAddress = CurationMother.IMAGE_ADDRESS

        )
        val content = objectMapper.writeValueAsString(updateCurationCommand)

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.patch("$BASE_URI/{curationId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "curation-update",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("curation")
                        .description("큐레이션 수정")
                        .pathParameters(
                            ResourceDocumentation.parameterWithName("curationId")
                                .description("큐레이션 식별자")
                        )
                        .requestFields(
                            PayloadDocumentation.fieldWithPath("title").type(JsonFieldType.STRING)
                                .description("수정할 큐레이션 제목"),
                            PayloadDocumentation.fieldWithPath("description")
                                .type(JsonFieldType.STRING)
                                .description("수정할 큐레이션 설명")
                        )
                )
            )
    }

    @Test
    fun delete() {
        // given

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.delete("$BASE_URI/{curationId}", 1)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "curation-delete",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("curation")
                        .description("큐레이션 삭제")
                        .pathParameters(
                            RequestDocumentation.parameterWithName("curationId")
                                .description("큐레이션 식별자")
                        )
                )
            )
    }

    @Test
    fun addStore() {
        // given
        val id = 1L
        val addStoreCommand = AddStoreCommand(Long.MAX_VALUE)
        val content = objectMapper.writeValueAsString(addStoreCommand)
        every { curationCommandService.addStore(id, any()) } returns id

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.post("$BASE_URI/{curationId}/store", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("body", Is.`is`(id.toInt())))
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "curation-create",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("curation")
                        .description("큐레이션 생성")
                        .pathParameters(
                            RequestDocumentation.parameterWithName("curationId")
                                .description("큐레이션 식별자")
                        )
                        .requestFields(
                            PayloadDocumentation.fieldWithPath("storeId").type(JsonFieldType.NUMBER)
                                .description("가게 식별자")
                        )
                        .responseFields(
                            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING)
                                .description("에러 메세지"),
                            PayloadDocumentation.fieldWithPath("body").type(JsonFieldType.NUMBER)
                                .description("가게를 추가한 큐레이션 식별자")
                        )
                )
            )
    }

    companion object {
        const val BASE_URI = "/api/v1/curations"
    }
}
