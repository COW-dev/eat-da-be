package com.mjucow.eatda.presentation.store.popularstore

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.mjucow.eatda.domain.poplarstore.service.PopularStoreQueryService
import com.mjucow.eatda.domain.poplarstore.service.dto.PopularStoreDtos
import com.mjucow.eatda.presentation.AbstractMockMvcTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.core.Is
import org.hamcrest.core.IsNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(PopularStoreController::class)
class PopularStoreControllerTest: AbstractMockMvcTest() {
    @MockkBean(relaxUnitFun = true)
    lateinit var popularStoreQueryService: PopularStoreQueryService

    @Test
    fun getPopularStores() {
        // given
        every { popularStoreQueryService.getPopularStores(any()) } returns PopularStoreDtos(
            emptyList()
        )

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(BASE_URI)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("message", Is.`is`(IsNull.nullValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("body").exists())
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "popular-store-get",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("store")
                        .description("인기 가게 조회")
                        .responseFields(
                            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메세지"),
                            PayloadDocumentation.fieldWithPath("body").type(JsonFieldType.OBJECT).description("조회된 인기 가게 리스트 객체"),
                            PayloadDocumentation.fieldWithPath("body.popularStores").type(JsonFieldType.ARRAY).description("조회된 인기 가게 리스트"),
                            PayloadDocumentation.fieldWithPath("body.popularStores[].id").type(JsonFieldType.NUMBER).description("가게 식별자"),
                            PayloadDocumentation.fieldWithPath("body.popularStores[].displayedName").type(JsonFieldType.STRING).description("보여지는 이름"),
                            PayloadDocumentation.fieldWithPath("body.popularStores[].address").type(JsonFieldType.STRING).description("가게 주소"),
                            PayloadDocumentation.fieldWithPath("body.popularStores[].phoneNumber").type(JsonFieldType.STRING).description("가게 연락처"),
                            PayloadDocumentation.fieldWithPath("body.popularStores[].imageAddress").type(JsonFieldType.STRING).description("가게 이미지 주소"),
                            PayloadDocumentation.fieldWithPath("body.popularStores[].location").type(JsonFieldType.OBJECT).description("가게 위치 정보"),
                            PayloadDocumentation.fieldWithPath("body.popularStores[].location.latitude").type(JsonFieldType.STRING).description("가게 위치 위도"),
                            PayloadDocumentation.fieldWithPath("body.popularStores[].location.longitude").type(JsonFieldType.STRING).description("가게 위치 경도"),
                            PayloadDocumentation.fieldWithPath("body.popularStores[].count").type(JsonFieldType.NUMBER).description("가게 조회수"),
                        )
                )
            )
    }

    companion object {
        const val BASE_URI = "/api/v1/stores/popular"
    }

}
