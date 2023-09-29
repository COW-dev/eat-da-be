package com.mjucow.eatda.presentation.store.category

import autoparams.kotlin.AutoKotlinSource
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.mjucow.eatda.AbstractMockMvcTest
import com.mjucow.eatda.domain.store.service.command.CategoryCommandService
import com.mjucow.eatda.domain.store.service.command.dto.UpdateNameCommand
import com.mjucow.eatda.domain.store.service.query.CategoryQueryService
import com.mjucow.eatda.domain.store.service.query.dto.Categories
import com.mjucow.eatda.domain.store.service.query.dto.CategoryDto
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.`when`
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CategoryController::class)
class CategoryMvcTest : AbstractMockMvcTest() {

    @MockBean
    lateinit var categoryQueryService: CategoryQueryService
    @MockBean
    lateinit var categoryCommandService: CategoryCommandService

    @ParameterizedTest
    @AutoKotlinSource
    fun findAll(categories: Categories) {
        // given
        `when`(categoryQueryService.findAll()).thenReturn(categories)

        // when & then
        mockMvc.perform(
            get(BASE_URI)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("message", `is`(IsNull.nullValue())))
            .andExpect(jsonPath("body").exists())
            .andExpect(jsonPath("body[0].id", `is`(categories[0].id)))
            .andExpect(jsonPath("body[0].name", `is`(categories[0].name)))
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "Category",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("category")
                        .description("카테고리 전체조회")
                        .responseFields(
                            fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메세지"),
                            fieldWithPath("body").type(JsonFieldType.ARRAY).description("카테고리 데이터들"),
                            fieldWithPath("body[].id").type(JsonFieldType.NUMBER).description("카테고리 식별자"),
                            fieldWithPath("body[].name").type(JsonFieldType.STRING).description("카테고리 이름"),
                        )
                )
            )
    }

    @ParameterizedTest
    @AutoKotlinSource
    fun findById(categoryDto: CategoryDto) {
        // given
        `when`(categoryQueryService.findById(anyLong())).thenReturn(categoryDto)

        // when & then
        mockMvc.perform(
            get("$BASE_URI/{cateogory-id}", categoryDto.id)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("body").exists())
            .andExpect(jsonPath("body.id", `is`(categoryDto.id)))
            .andExpect(jsonPath("body.name", `is`(categoryDto.name)))
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "Category",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("category")
                        .description("카테고리 단건 조회")
                        .pathParameters(
                            parameterWithName("category-id").description("카테고리 식별자")
                        )
                        .responseFields(
                            fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메세지"),
                            fieldWithPath("body").type(JsonFieldType.OBJECT).description("카테고리 데이터"),
                            fieldWithPath("body.id").type(JsonFieldType.NUMBER).description("카테고리 식별자"),
                            fieldWithPath("body.name").type(JsonFieldType.STRING).description("카테고리 이름"),
                        )
                )
            )
    }

//    @ParameterizedTest
//    @AutoKotlinSource
//    fun create(id: Long) {
//        // given
//        val createCommand = CreateCommand("validName")
//        val content = objectMapper.writeValueAsString(createCommand)
//        `when`(categoryCommandService.create(any())).thenReturn(id)
//
//        // when & then
//        mockMvc.perform(
//            post(BASE_URI)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content)
//        )
//            .andExpect(status().isCreated)
//            .andExpect(jsonPath("body", `is`(id)))
//            .andDo(
//                MockMvcRestDocumentationWrapper.document(
//                    identifier = "Category",
//                    resourceDetails = ResourceSnippetParametersBuilder()
//                        .tag("category")
//                        .description("카테고리 생성")
//                        .requestFields(
//                            fieldWithPath("name").type(JsonFieldType.STRING).description("카테고리 이름"),
//                        )
//                        .responseFields(
//                            fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메세지"),
//                            fieldWithPath("body").type(JsonFieldType.NUMBER).description("생성된 카테고리 식별자"),
//                        )
//                )
//            )
//    }

    @Test
    fun delete() {
        // given

        // when & then
        mockMvc.perform(
            delete("$BASE_URI/{category-id}", "1")
        )
            .andExpect(status().isNoContent)
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "Category",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("category")
                        .description("카테고리 삭제")
                        .pathParameters(
                            parameterWithName("category-id").description("카테고리 식별자")
                        )
                )
            )
    }

    @Test
    fun updateName() {
        // given
        val id = 1L
        val newName = "newName"
        val updateNameCommand = UpdateNameCommand(newName)
        val content = objectMapper.writeValueAsString(updateNameCommand)

        // when & then
        mockMvc.perform(
            patch("$BASE_URI/{category-id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "Category",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("category")
                        .description("카테고리 이름 수정")
                        .pathParameters(
                            parameterWithName("category-id").description("카테고리 식별자")
                        )
                        .requestFields(
                            fieldWithPath("name").type(JsonFieldType.STRING).description("카테고리 이름"),
                        )
                )
            )
    }

    companion object {
        const val BASE_URI = "/api/v1/categories"
    }
}
