package com.mjucow.eatda.presentation.store.menu

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.mjucow.eatda.domain.store.entity.objectmother.MenuMother
import com.mjucow.eatda.domain.store.service.command.MenuCommandService
import com.mjucow.eatda.domain.store.service.command.dto.MenuUpdateCommand
import com.mjucow.eatda.domain.store.service.query.MenuDto
import com.mjucow.eatda.domain.store.service.query.MenuQueryService
import com.mjucow.eatda.presentation.AbstractMockMvcTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(MenuController::class)
class MenuControllerMvcTest : AbstractMockMvcTest() {
    @MockkBean(relaxUnitFun = true)
    lateinit var queryService: MenuQueryService

    @MockkBean(relaxUnitFun = true)
    lateinit var commandService: MenuCommandService

    @Test
    fun update() {
        // given
        val menuId = 1L
        val command = MenuUpdateCommand(name = MenuMother.NAME, price = MenuMother.PRICE)
        val content = objectMapper.writeValueAsString(command)

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.patch("$BASE_URI/{menuId}", menuId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "menu-update",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("menu")
                        .description("메뉴 수정")
                        .pathParameters(
                            ResourceDocumentation.parameterWithName("menuId").description("메뉴 식별자")
                        )
                        .requestFields(
                            PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("새로운 메뉴 이름"),
                            PayloadDocumentation.fieldWithPath("price").type(JsonFieldType.NUMBER).description("새로운 메뉴 가격"),
                            PayloadDocumentation.fieldWithPath("imageAddress").type(JsonFieldType.STRING).description("새로운 메뉴 이미지 주소").optional()
                        )
                )
            )
    }

    @Test
    fun findById() {
        // given
        val menuId = 1L
        val entity = MenuMother.createWithId(id = menuId)
        every { queryService.findById(any()) } returns MenuDto.from(entity)

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.get("$BASE_URI/{menuId}", menuId)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "menu-findById",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("menu")
                        .description("메뉴 조회")
                        .pathParameters(
                            ResourceDocumentation.parameterWithName("menuId").description("메뉴 식별자")
                        )
                        .responseFields(
                            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메세지"),
                            PayloadDocumentation.fieldWithPath("body").type(JsonFieldType.OBJECT).description("메뉴"),
                            PayloadDocumentation.fieldWithPath("body.id").type(JsonFieldType.NUMBER).description("메뉴 식별자"),
                            PayloadDocumentation.fieldWithPath("body.name").type(JsonFieldType.STRING).description("메뉴 이름"),
                            PayloadDocumentation.fieldWithPath("body.price").type(JsonFieldType.NUMBER).description("메뉴 가격"),
                            PayloadDocumentation.fieldWithPath("body.imageAddress").type(JsonFieldType.STRING).description("메뉴 이미지 주소")
                        )
                )
            )
    }

    @Test
    fun deleteById() {
        // given
        val menuId = 1L

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.delete("$BASE_URI/{menuId}", menuId)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "menu-deleteById",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("menu")
                        .description("메뉴 삭제")
                        .pathParameters(
                            ResourceDocumentation.parameterWithName("menuId").description("메뉴 식별자")
                        )
                )
            )
    }

    companion object {
        const val BASE_URI = "/api/v1/menu"
    }
}
