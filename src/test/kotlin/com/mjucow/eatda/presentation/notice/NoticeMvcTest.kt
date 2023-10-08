package com.mjucow.eatda.presentation.notice

import autoparams.kotlin.AutoKotlinSource
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.mjucow.eatda.domain.notice.service.command.NoticeCommandService
import com.mjucow.eatda.domain.notice.service.command.dto.CreateNoticeCommand
import com.mjucow.eatda.domain.notice.service.command.dto.UpdateNoticeCommand
import com.mjucow.eatda.domain.notice.service.query.NoticeQueryService
import com.mjucow.eatda.domain.notice.service.query.dto.NoticeDto
import com.mjucow.eatda.domain.notice.service.query.dto.Notices
import com.mjucow.eatda.presentation.AbstractMockMvcTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(NoticeController::class)
class NoticeMvcTest : AbstractMockMvcTest() {
    @MockkBean(relaxUnitFun = true)
    lateinit var noticeQueryService: NoticeQueryService

    @MockkBean(relaxUnitFun = true)
    lateinit var noticeCommandService: NoticeCommandService

    @ParameterizedTest
    @AutoKotlinSource
    fun findAll(notices: Notices) {
        // given
        every { noticeQueryService.findAll() } returns notices

        // when & then
        mockMvc.perform(
            get(BASE_URI)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("message", `is`(IsNull.nullValue())))
            .andExpect(jsonPath("body").exists())
            .andExpect(jsonPath("body[0].id", `is`(notices[0].id)))
            .andExpect(jsonPath("body[0].title", `is`(notices[0].title)))
            .andExpect(jsonPath("body[0].content", `is`(notices[0].content)))
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "notice-findAll",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("notice")
                        .description("공지사항 전체조회")
                        .responseFields(
                            fieldWithPath("message").type(JsonFieldType.STRING)
                                .description("에러 메세지"),
                            fieldWithPath("body").type(JsonFieldType.ARRAY)
                                .description("공지사항 데이터들"),
                            fieldWithPath("body[].id").type(JsonFieldType.NUMBER)
                                .description("공지사항 식별자"),
                            fieldWithPath("body[].title").type(JsonFieldType.STRING)
                                .description("공지사항 제목"),
                            fieldWithPath("body[].content").type(JsonFieldType.STRING)
                                .description("공지사항 내용")
                        )
                )
            )
    }

    @ParameterizedTest
    @AutoKotlinSource
    fun findById(noticeDto: NoticeDto) {
        // given
        every { noticeQueryService.findById(any()) } returns noticeDto

        // when & then
        mockMvc.perform(
            get("$BASE_URI/{noticeId}", noticeDto.id)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("body").exists())
            .andExpect(jsonPath("body.id", `is`(noticeDto.id)))
            .andExpect(jsonPath("body.title", `is`(noticeDto.title)))
            .andExpect(jsonPath("body.content", `is`(noticeDto.content)))
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "notice-findById",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("notice")
                        .description("공지사항 단건 조회")
                        .pathParameters(
                            ResourceDocumentation.parameterWithName("noticeId").description("공지사항 식별자")
                        )
                        .responseFields(
                            fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메세지"),
                            fieldWithPath("body").type(JsonFieldType.OBJECT).description("공지사항 데이터"),
                            fieldWithPath("body.id").type(JsonFieldType.NUMBER).description("공지사항 식별자"),
                            fieldWithPath("body.title").type(JsonFieldType.STRING).description("공지사항 제목"),
                            fieldWithPath("body.content").type(JsonFieldType.STRING).description("공지사항 내용")
                        )
                )
            )
    }

    @ParameterizedTest
    @AutoKotlinSource
    fun create(id: Long) {
        // given
        val createNoticeCommand = CreateNoticeCommand("title", "content")
        val content = objectMapper.writeValueAsString(createNoticeCommand)

        every { noticeCommandService.create(any()) } returns id

        // when & then
        mockMvc.perform(
            post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("body", `is`(id)))
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "notice-create",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("notice")
                        .description("공지사항 생성")
                        .requestFields(
                            fieldWithPath("title").type(JsonFieldType.STRING).description("공지사항 제목"),
                            fieldWithPath("content").type(JsonFieldType.STRING).description("공지사항 내용")
                        )
                        .responseFields(
                            fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메세지"),
                            fieldWithPath("body").type(JsonFieldType.NUMBER).description("생성된 공지사항 식별자")
                        )
                )
            )
    }

    @Test
    fun delete() {
        // given

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.delete("$BASE_URI/{noticeId}", 1)
        )
            .andExpect(status().isNoContent)
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "notice-delete",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("notice")
                        .description("공지사항 삭제")
                        .pathParameters(
                            ResourceDocumentation.parameterWithName("noticeId").description("공지사항 식별자")
                        )
                )
            )
    }

    @Test
    fun update() {
        // given
        val id = 1L
        val newTitle = "newTitle"
        val newContent = "newContent"
        val updateNoticeCommand = UpdateNoticeCommand(newTitle, newContent)
        val content = objectMapper.writeValueAsString(updateNoticeCommand)

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.patch("$BASE_URI/{noticeId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "notice-update",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("notice")
                        .description("공지사항 수정")
                        .pathParameters(
                            ResourceDocumentation.parameterWithName("noticeId").description("공지사항 식별자")
                        )
                        .requestFields(
                            fieldWithPath("title").type(JsonFieldType.STRING).description("공지사항 제목")
                        )
                        .requestFields(
                            fieldWithPath("content").type(JsonFieldType.STRING).description("공지사항 내용")
                        )
                )
            )
    }

    companion object {
        const val BASE_URI = "/api/v1/notices"
    }
}
