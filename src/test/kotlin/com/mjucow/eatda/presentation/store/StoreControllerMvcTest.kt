package com.mjucow.eatda.presentation.store

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.mjucow.eatda.domain.store.entity.objectmother.StoreMother
import com.mjucow.eatda.domain.store.service.command.StoreCommandService
import com.mjucow.eatda.domain.store.service.command.dto.StoreCreateCommand
import com.mjucow.eatda.domain.store.service.command.dto.StoreUpdateCommand
import com.mjucow.eatda.domain.store.service.query.StoreQueryService
import com.mjucow.eatda.domain.store.service.query.dto.StoreDetailDto
import com.mjucow.eatda.domain.store.service.query.dto.StoreDto
import com.mjucow.eatda.presentation.AbstractMockMvcTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.SliceImpl
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.stream.IntStream

@WebMvcTest(StoreController::class)
class StoreControllerMvcTest : AbstractMockMvcTest() {
    @MockkBean(relaxUnitFun = true)
    lateinit var storeQueryService: StoreQueryService

    @MockkBean(relaxUnitFun = true)
    lateinit var storeCommandService: StoreCommandService

    @Test
    fun create() {
        // given
        val entityId = 1L
        every { storeCommandService.create(any()) } returns entityId
        val command = StoreCreateCommand(name = StoreMother.NAME, address = StoreMother.ADDRESS)
        val content = objectMapper.writeValueAsString(command)

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("message", `is`(IsNull.nullValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("body", `is`(entityId), Long::class.java))
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "store-create",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("Store")
                        .description("가게 생성")
                        .responseFields(
                            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메세지"),
                            PayloadDocumentation.fieldWithPath("body").type(JsonFieldType.NUMBER).description("가게 식별자")
                        )
                )
            )
    }

    @Test
    fun findAllByCursor() {
        // given
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

        every { storeQueryService.findAllByCategoryAndCursor(any(), any(), any()) } returns SliceImpl(storeDtos)

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(
                "$BASE_URI?storeId={storeId}&size={size}&categoryId={categoryId}",
                storeDtos.size + 1,
                pageSize,
                null
            )
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("message", `is`(IsNull.nullValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("body").exists())
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "store-findAllByCursor",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("Store")
                        .description("커서 기반 카테고리 가게 조회")
                        .queryParameters(
                            ResourceDocumentation.parameterWithName("storeId").description("조회한 마지막 가게 식별자").optional(),
                            ResourceDocumentation.parameterWithName("categoryId").description("조회하는 가게의 카테고리의 식별자").optional(),
                            ResourceDocumentation.parameterWithName("size").description("조회할 페이지 사이즈").optional()
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
    fun findById() {
        // given
        val store = StoreMother.createWithId()
        every { storeQueryService.findById(store.id) } returns StoreDetailDto.from(store)

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.get("$BASE_URI/{storeId}", store.id)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("message", `is`(IsNull.nullValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("body").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("body.id", `is`(store.id), Long::class.java))
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "store-findById",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("Store")
                        .description("특정 가게 조회")
                        .pathParameters(
                            ResourceDocumentation.parameterWithName("storeId").description("가게 식별자")
                        )
                        .responseFields(
                            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메세지"),
                            PayloadDocumentation.fieldWithPath("body").type(JsonFieldType.OBJECT).description("가게"),
                            PayloadDocumentation.fieldWithPath("body.id").type(JsonFieldType.NUMBER).description("가게 식별자"),
                            PayloadDocumentation.fieldWithPath("body.name").type(JsonFieldType.STRING).description("가게 이름"),
                            PayloadDocumentation.fieldWithPath("body.address").type(JsonFieldType.STRING).description("가게 주소"),
                            PayloadDocumentation.fieldWithPath("body.phoneNumber").type(JsonFieldType.STRING).description("가게 연락처"),
                            PayloadDocumentation.fieldWithPath("body.imageAddress").type(JsonFieldType.STRING).description("가게 이미지 주소"),
                            PayloadDocumentation.fieldWithPath("body.location").type(JsonFieldType.OBJECT).description("가게 위치 정보"),
                            PayloadDocumentation.fieldWithPath("body.location.latitude").type(JsonFieldType.STRING).description("가게 위치 위도"),
                            PayloadDocumentation.fieldWithPath("body.location.longitude").type(JsonFieldType.STRING).description("가게 위치 경도"),
                            PayloadDocumentation.fieldWithPath("body.categories").type(JsonFieldType.ARRAY).description("가게의 카테고리들"),
                            PayloadDocumentation.fieldWithPath("body.categories[0].id").type(JsonFieldType.STRING).description("카테고리 식별자"),
                            PayloadDocumentation.fieldWithPath("body.categories[0].name").type(JsonFieldType.STRING).description("카테고리 이름"),
                            PayloadDocumentation.fieldWithPath("body.storeHours").type(JsonFieldType.ARRAY).description("가게의 영업시간들"),
                            PayloadDocumentation.fieldWithPath("body.storeHours[0].dayOfWeek").type(JsonFieldType.STRING).description("영업 요일"),
                            PayloadDocumentation.fieldWithPath("body.storeHours[0].openAt").type(JsonFieldType.STRING).description("영업 시작시간"),
                            PayloadDocumentation.fieldWithPath("body.storeHours[0].closeAt").type(JsonFieldType.STRING).description("영업 종료시간")
                        )
                )
            )
    }

    @Test
    fun updateById() {
        // given
        val id = 1L
        val command = StoreUpdateCommand(
            name = StoreMother.NAME,
            address = StoreMother.ADDRESS,
            displayName = StoreMother.DISPLAY_NAME,
            phoneNumber = StoreMother.PHONE_NUMBER,
            imageAddress = StoreMother.IMAGE_ADDRESS,
            location = StoreMother.LOCATION
        )
        val content = objectMapper.writeValueAsString(command)

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.patch("$BASE_URI/{storeId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "store-updateById",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("Store")
                        .description("특정 가게 정보 업데이트")
                        .pathParameters(
                            ResourceDocumentation.parameterWithName("storeId").description("가게 식별자")
                        )
                        .requestFields(
                            PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("새로운 가게 이름"),
                            PayloadDocumentation.fieldWithPath("address").type(JsonFieldType.STRING).description("새로운 가게 주소"),
                            PayloadDocumentation.fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("새로운 가게 연락처"),
                            PayloadDocumentation.fieldWithPath("imageAddress").type(JsonFieldType.STRING).description("새로운 가게 이미지 주소"),
                            PayloadDocumentation.fieldWithPath("location").type(JsonFieldType.OBJECT).description("새로운 가게 위치 정보"),
                            PayloadDocumentation.fieldWithPath("location.latitude").type(JsonFieldType.STRING).description("새로운 가게 위치 위도"),
                            PayloadDocumentation.fieldWithPath("location.longitude").type(JsonFieldType.STRING).description("새로운 가게 위치 경도")
                        )
                )
            )
    }

    @Test
    fun deleteById() {
        // given
        val id = 1L

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.delete("$BASE_URI/{storeId}", id)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "store-deleteById",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("Store")
                        .description("특정 가게 정보 삭제")
                        .pathParameters(
                            ResourceDocumentation.parameterWithName("storeId").description("가게 식별자")
                        )
                )
            )
    }

    companion object {
        const val BASE_URI = "/api/v1/stores"
    }
}
