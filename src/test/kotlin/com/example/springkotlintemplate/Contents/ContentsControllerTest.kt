package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.Exception.ContentsNotFoundException
import com.example.springkotlintemplate.Contents.VO.Contents
import com.example.springkotlintemplate.MockMvcConfig
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.client.match.MockRestRequestMatchers.content
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Import(MockMvcConfig::class)
class ContentsControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val mockContentsService: ContentsService,
    @Autowired private val restDocumentation : ManualRestDocumentation,
) :DescribeSpec({
    beforeEach {
        restDocumentation.beforeTest(ContentsControllerTest::class.java,it.name.testName)
    }
    afterEach {
        restDocumentation.afterTest()
    }

    val mockContents= Contents()
    val jsonMockContents= ObjectMapper().writeValueAsString(mockContents)
    val URI="/api/v1/contents"

    describe("ContentsController") {
        context("GET $URI/{folderName}") {
            it("folderName 에 해당하는 폴더 트리를 반환해야한다.") {
                every { mockContentsService.findAllByFolderName(any()) } returns listOf(mockContents)
                mockMvc.perform(
                    get("$URI/{folderName}", "test")
                ).andExpect {
                    status().isOk
                    content().json(jsonMockContents)
                    content().contentType(MediaType.APPLICATION_JSON)
                }
                    .andDo(document("contents-get-all-by-folder-name"
                        , preprocessRequest(prettyPrint())
                        , preprocessResponse(prettyPrint())
                        , pathParameters(
                            parameterWithName("folderName").description("폴더 이름")
                        ),
                        responseFields(mockContentsResponseFields("[]."))
                    ))
            }
        }
        context("POST $URI") {
            it("컨텐츠를 만들고 결과를 반환해야한다.") {
                every { mockContentsService.create(any()) } returns mockContents
                mockMvc.perform(
                    post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMockContents)
                ).andExpect {
                    status().isOk
                    content().json(jsonMockContents)
                    content().contentType(MediaType.APPLICATION_JSON)
                }
                    .andDo(document("contents-create"
                        , preprocessRequest(prettyPrint())
                        , preprocessResponse(prettyPrint())
                        , requestFields(mockContentsResponseFields())
                        , responseFields(mockContentsResponseFields())
                    ))
            }
            it("컨텐츠 데이터 요청 형식이 잘못된 경우 415 에러를 반환한다.") {
                val invalidContents = "this is invalid contents"
                mockMvc.post(URI) {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                    content = invalidContents
                }.andExpect {
                    status { isUnsupportedMediaType() }
                }
            }
            it("컨텐츠를 만드는 과정에서 서버에 에러가 발생한 경우 500 에러와 메시지를 반환한다."){
                every { mockContentsService.create(any()) } throws Exception("error")
                mockMvc.post(URI) {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                    content = jsonMockContents
                }.andExpect {
                    status { isInternalServerError() }
                    content { string("error") }
                }
            }
        }
        context("PUT $URI/{contentsId}") {
            every { mockContentsService.update(any(), any()) } returns mockContents
            it("컨텐츠를 수정하고 결과를 반환해야한다.") {
                mockMvc.perform(
                    put("$URI/{contentsId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMockContents)
                ).andExpect {
                    status().isOk
                    content().json(jsonMockContents)
                    content().contentType(MediaType.APPLICATION_JSON)
                }
                    .andDo(document("contents-update"
                        , preprocessRequest(prettyPrint())
                        , preprocessResponse(prettyPrint())
                        , pathParameters(
                            parameterWithName("contentsId").description("컨텐츠 아이디")
                        ),
                        requestFields(mockContentsResponseFields())
                        , responseFields(mockContentsResponseFields())
                    ))
            }
            it("컨텐츠 데이터 요청 형식이 잘못된 경우 415 에러를 반환한다.") {
                val invalidContents = "this is invalid contents"
                mockMvc.put("$URI/1") {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                    content = invalidContents
                }.andExpect {
                    status { isUnsupportedMediaType() }
                }
            }
            it("컨텐츠를 수정하는 과정에서 서버에 에러가 발생한 경우 500 에러와 메시지를 반환한다."){
                every { mockContentsService.update(any(), any()) } throws Exception("error")
                mockMvc.put("$URI/1") {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                    content = jsonMockContents
                }.andExpect {
                    status { isInternalServerError() }
                    content { string("error") }
                }
            }
        }
        context("DELETE $URI/{contentsId}"){
            every { mockContentsService.delete( any()) } returns Unit
            it("컨텐츠를 삭제하고 결과를 반환해야한다.") {
                mockMvc.perform(
                    delete("$URI/{contentsId}", 1)
                ).andExpect {
                    status().isOk
                }.andDo(
                    document("contents-delete"
                        , preprocessRequest(prettyPrint())
                        , preprocessResponse(prettyPrint())
                        , pathParameters(
                            parameterWithName("contentsId").description("컨텐츠 아이디")
                        )
                    )
                )
            }
            it("삭제할 컨텐츠가 없는 경우 컨텐츠가 없다는 메시지를 반환한다.") {
                every { mockContentsService.delete(any()) } throws ContentsNotFoundException()
                mockMvc.delete("$URI/1") .andExpect {
                    status { isInternalServerError() }
                    content { string("컨텐츠가 없습니다.") }
                }
            }
            it("컨텐츠를 삭제하는 과정에서 서버에 에러가 발생한 경우 500 에러와 메시지를 반환한다."){
                every { mockContentsService.delete( any()) } throws Exception("error")
                mockMvc.delete("$URI/1").andExpect {
                    status { isInternalServerError() }
                    content { string("error") }
                }
            }

        }
    }
})

fun mockContentsResponseFields(prefix : String="") = listOf(
    fieldWithPath("$prefix.id").description("컨텐츠 ID"),
    fieldWithPath("$prefix.folder").description("폴더 정보"),
    fieldWithPath("$prefix.folder.name").description("폴더 이름"),
    fieldWithPath("$prefix.folder.children").description("하위 폴더 정보"),
    fieldWithPath("$prefix.question").description("질문"),
    fieldWithPath("$prefix.answer").description("답안"),
    fieldWithPath("$prefix.reference").description("참고자료"),
    fieldWithPath("$prefix.rationale").description("이유"),
    fieldWithPath("$prefix.rationale.id").description("이유 ID"),
    fieldWithPath("$prefix.rationale.url").description("이유 URL 리스트"),
    fieldWithPath("$prefix.rationale.description").description("이유 참고자료 ID"),
    fieldWithPath("$prefix.writeDate").description("작성일"),
    fieldWithPath("$prefix.writer").description("작성자"),
    fieldWithPath("$prefix.writer.id").description("작성자 ID"),
    fieldWithPath("$prefix.writer.name").description("작성자 이름"),
    fieldWithPath("$prefix.keyword").description("키워드 리스트"),
    fieldWithPath("$prefix.keyword[]").description("키워드"),
    fieldWithPath("$prefix.review").description("리뷰"),
    fieldWithPath("$prefix.review.id").description("리뷰 ID"),
    fieldWithPath("$prefix.review.reviewComment").description("리뷰 설명"),
    fieldWithPath("$prefix.review.reviewDate").description("리뷰 날짜"),
    fieldWithPath("$prefix.review.reviewer").description("리뷰 작성자"),
    fieldWithPath("$prefix.review.reviewer.id").description("리뷰 작성자 ID"),
    fieldWithPath("$prefix.review.reviewer.name").description("리뷰 작성자 이름"),
    fieldWithPath("$prefix.state").description("리뷰 상태"),
)