package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeRequestDto
import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeResponseDto
import com.example.springkotlintemplate.FolderTree.Entity.FolderTree
import com.example.springkotlintemplate.MockMvcConfig
import com.google.gson.Gson
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.client.match.MockRestRequestMatchers.content
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@Import(MockMvcConfig::class)
class FolderTreeControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val mockService: FolderTreeService,
    @Autowired private val restDocumentation : ManualRestDocumentation
): DescribeSpec({
    beforeEach {
        restDocumentation.beforeTest(FolderTreeController::class.java,it.name.testName)
    }
    afterEach {
        restDocumentation.afterTest()
    }
    val URI="/api/v1/folder"
    val mockFolderTree = FolderTreeRequestDto("root", mutableListOf(
        FolderTreeRequestDto("child1", mutableListOf())
    ))
    val mockFolderTreeResponse = FolderTreeResponseDto("root", mutableListOf(
        FolderTreeResponseDto("child1", mutableListOf())
    ))
    describe("FolderTreeController") {
        context("GET /api/folder") {
            it("모든 폴더 트리 리스트를 반환해야한다.") {
                every { mockService.findAll() } returns listOf(mockFolderTreeResponse)
                mockMvc.perform(
                    get(URI)
                ).andExpect {
                    status().isOk
                    content().json(Gson().toJson(listOf(mockFolderTree)))
                    content().contentType(MediaType.APPLICATION_JSON)
                }.andDo(document("folder-tree-get",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("[].name").description("폴더 이름"),
                        fieldWithPath("[].children").description("하위 폴더 리스트, 없으면 빈 리스트"),
                        fieldWithPath("[].children[].name").description("하위 폴더 이름"),
                        fieldWithPath("[].children[].children").description("하위 폴더의 하위 폴더 리스트, 없으면 빈 리스트"),
                    ),
                ))
            }
            it("만약 폴더 트리 리스트가 없다면 빈 리스트를 반환해야한다.") {
                every { mockService.findAll() } returns listOf()
                mockMvc.get(URI) {
                    accept = MediaType.APPLICATION_JSON
                }.andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    content { string("[]") }
                }
            }
        }
        context("POST /api/folder") {
            it("폴더 트리를 만들고 결과를 반환해야한다.") {
                every { mockService.create(any()) } returns mockFolderTreeResponse
                mockMvc.perform(
                    post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Gson().toJson(mockFolderTree))
                ).andExpect {
                    status().isOk
                    content().json(Gson().toJson(mockFolderTree))
                    content().contentType(MediaType.APPLICATION_JSON)
                }.andDo(document("folder-tree-post",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("name").description("폴더 이름"),
                        fieldWithPath("children").description("하위 폴더 리스트, 없으면 빈 리스트"),
                        fieldWithPath("children[].name").description("하위 폴더 이름"),
                        fieldWithPath("children[].children").description("하위 폴더의 하위 폴더 리스트, 없으면 빈 리스트"),
                    )
                ))
            }
            it("요청 형식이 폴더 트리 형식이 아닌 경우 400 에러와 형식이 잘못되었다는 메시지를 반환해야한다.") {
                mockMvc.post(URI) {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                    content = Gson().toJson("not a folder tree")
                }.andExpect {
                    status { isUnsupportedMediaType() }
                }
            }
            it("폴더 트리 생성을 실패하면, 500 에러와 메시지를 반환해야한다.") {
                every { mockService.create(any()) } throws Exception("폴더 트리 생성 실패")
                mockMvc.post(URI) {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                    content = Gson().toJson(mockFolderTree)
                }.andExpect {
                    status { isInternalServerError() }
                    content {
                        string("폴더 트리 생성 실패")
                    }
                }
            }
        }
        context("PUT /api/folder/{id}") {
            it("업데이트된 폴더 트리를 반환해야한다.") {
                every { mockService.update(any(), any()) } returns mockFolderTreeResponse
                mockMvc.perform(
                    put("$URI/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Gson().toJson(mockFolderTree))
                ).andExpect {
                    status().isOk
                    content().json(Gson().toJson(mockFolderTree))
                    content().contentType(MediaType.APPLICATION_JSON)
                }.andDo(document("folder-tree-put",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("name").description("폴더 이름"),
                        fieldWithPath("children").description("하위 폴더 리스트, 없으면 빈 리스트"),
                        fieldWithPath("children[].name").description("하위 폴더 이름"),
                        fieldWithPath("children[].children").description("하위 폴더의 하위 폴더 리스트, 없으면 빈 리스트"),
                    )
                ))
            }
            it("폴더 트리 요청 형식이 잘못되었다면, 400 에러와 형식이 잘못되었다는 메시지를 반환해야한다.") {
                every { mockService.update(any(), any()) } returns mockFolderTreeResponse
                mockMvc.put("$URI/1") {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                    content = Gson().toJson("not a folder tree")
                }.andExpect {
                    status { isUnsupportedMediaType() }
                }
            }
            it("폴더 트리 업데이트를 실패했다면, 500 에러를 반환해야한다.") {
                every { mockService.update(any(), any()) } throws Exception("폴더 트리 업데이트 실패")
                mockMvc.put("$URI/1") {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                    content = Gson().toJson(mockFolderTree)
                }.andExpect {
                    status { isInternalServerError() }
                    content {
                        contentType(MediaType.APPLICATION_JSON_UTF8)
                        string("폴더 트리 업데이트 실패")
                    }
                }
            }
        }
        context("DELETE /api/folder/{id}") {
            it("폴더 트리 삭제를 성공하면, 200 응답을 반환해야한다.") {
                every { mockService.delete(any()) } returns Unit
                mockMvc.perform(
                    delete("$URI/1")
                ).andExpect {
                    status().isOk
                }.andDo(document("folder-tree-delete"))
            }
            it("폴더 트리 삭제를 실패한다면, 500 에러와 메시지를 반환해야한다.") {
                every { mockService.delete(any()) } throws Exception("폴더 트리 삭제 실패")
                mockMvc.delete("$URI/1") {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                }.andExpect {
                    status { isInternalServerError() }
                    content {
                        contentType(MediaType.APPLICATION_JSON_UTF8)
                        string("폴더 트리 삭제 실패")
                    }
                }
            }
        }
    }
})
