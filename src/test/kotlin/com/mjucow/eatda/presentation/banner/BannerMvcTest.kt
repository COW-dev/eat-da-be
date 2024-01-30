package com.mjucow.eatda.presentation.banner

import autoparams.kotlin.AutoKotlinSource
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.mjucow.eatda.domain.banner.entity.objectmother.BannerMother
import com.mjucow.eatda.domain.banner.service.command.BannerCommandService
import com.mjucow.eatda.domain.banner.service.command.dto.CreateBannerCommand
import com.mjucow.eatda.domain.banner.service.command.dto.UpdateBannerCommand
import com.mjucow.eatda.domain.banner.service.query.BannerQueryService
import com.mjucow.eatda.domain.banner.service.query.dto.BannerDto
import com.mjucow.eatda.domain.banner.service.query.dto.Banners
import com.mjucow.eatda.presentation.AbstractMockMvcTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BannerController::class)
class BannerMvcTest : AbstractMockMvcTest() {
    @MockkBean(relaxUnitFun = true)
    lateinit var bannerQueryService: BannerQueryService

    @MockkBean(relaxUnitFun = true)
    lateinit var bannerCommandService: BannerCommandService

    @Test
    fun findAll() {
        // given
        val entityId = 1L
        val banners = Banners(listOf(BannerDto.from(BannerMother.createWithId(id = entityId))))
        every { bannerQueryService.findAll() } returns banners

        // when & then
        mockMvc.perform(
            get(BASE_URI)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("message", `is`(IsNull.nullValue())))
            .andExpect(jsonPath("body").exists())
            .andDo(
                document(
                    identifier = "banner-findAll",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("banner")
                        .description("배너 전체 조회")
                        .responseFields(
                            fieldWithPath("message").type(JsonFieldType.STRING)
                                .description("에러 메세지"),
                            fieldWithPath("body").type(JsonFieldType.ARRAY).description("배너 데이터"),
                            fieldWithPath("body[].id").type(JsonFieldType.NUMBER)
                                .description("배너 식별자"),
                            fieldWithPath("body[].link").type(JsonFieldType.STRING)
                                .description("배너 링크 주소"),
                            fieldWithPath("body[].imageAddress").type(JsonFieldType.STRING)
                                .description("배너 이미지"),
                            fieldWithPath("body[].expiredAt").type(JsonFieldType.STRING)
                                .description("배너 만료일자")
                        )
                )
            )
    }

    @ParameterizedTest
    @AutoKotlinSource
    fun create(id: Long) {
        // given
        val createBannerCommand = CreateBannerCommand(
            link = BannerMother.LINK,
            imageAddress = BannerMother.IMAGE_ADDRESS,
            expiredAt = BannerMother.EXPIRED_AT
        )
        val content = objectMapper.writeValueAsString(createBannerCommand)
        every { bannerCommandService.create(any()) } returns id

        // when & then
        mockMvc.perform(
            post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("body", `is`(id)))
            .andDo(
                document(
                    identifier = "banner-create",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("banner")
                        .description("배너 생성")
                        .requestFields(
                            fieldWithPath("link").type(JsonFieldType.STRING)
                                .description("배너 링크 주소"),
                            fieldWithPath("imageAddress").type(JsonFieldType.STRING)
                                .description("배너 이미지"),
                            fieldWithPath("expiredAt").type(JsonFieldType.STRING)
                                .description("배너 만료일자")
                        )
                        .responseFields(
                            fieldWithPath("message").type(JsonFieldType.STRING)
                                .description("에러 메세지"),
                            fieldWithPath("body").type(JsonFieldType.NUMBER)
                                .description("생성된 배너 식별자")
                        )
                )
            )
    }

    @Test
    fun update() {
        // given
        val id = 1L
        val updateBannerCommand = UpdateBannerCommand(
            link = BannerMother.LINK,
            imageAddress = BannerMother.IMAGE_ADDRESS,
            expiredAt = BannerMother.EXPIRED_AT
        )
        val content = objectMapper.writeValueAsString(updateBannerCommand)

        // when & then
        mockMvc.perform(
            patch("$BASE_URI/{bannerId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
            .andExpect(status().isOk)
            .andDo(
                document(
                    identifier = "banner-update",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("banner")
                        .description("배너 수정")
                        .pathParameters(
                            ResourceDocumentation.parameterWithName("baennrId")
                                .description("배너 식별자")
                        )
                        .requestFields(
                            fieldWithPath("link").type(JsonFieldType.STRING)
                                .description("배너 링크 주소"),
                            fieldWithPath("imageAddress").type(JsonFieldType.STRING)
                                .description("배너 이미지"),
                            fieldWithPath("expiredAt").type(JsonFieldType.STRING)
                                .description("배너 만료일자")
                        )
                )
            )
    }

    @Test
    fun delete() {
        // given

        // when & then
        mockMvc.perform(
            delete("$BASE_URI/{bannerId}", 1)
        )
            .andExpect(status().isNoContent)
            .andDo(
                document(
                    identifier = "banner-delete",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("banner")
                        .description("배너 삭제")
                        .pathParameters(
                            parameterWithName("bannerId").description("배너 식별자")
                        )
                )
            )
    }

    companion object {
        const val BASE_URI = "/api/v1/banners"
    }
}
