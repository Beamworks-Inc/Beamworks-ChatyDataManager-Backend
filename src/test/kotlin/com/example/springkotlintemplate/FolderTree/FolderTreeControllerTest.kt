package com.example.springkotlintemplate.FolderTree

import com.google.gson.Gson
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "localhost")
class FolderTreeControllerTest: DescribeSpec({
    val mockService = mockk<FolderTreeService>()
    val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(FolderTreeController()).build()
    val URI="/api/folder"
    val mockFolderTree = FolderTree("root", listOf(
        FolderTree("child1", listOf(
            FolderTree("grandchild1", listOf()),
            FolderTree("grandchild2", listOf())
        )),
        FolderTree("child2", listOf())
    ))

    describe("FolderTreeController") {
        context("GET /api/folder") {
            it("should return all folderTree") {
                every { mockService.findAll() } returns listOf(mockFolderTree)
                mockMvc.perform(
                    RestDocumentationRequestBuilders.get(URI)
                )
                    .andExpect {
                        status().isOk
                        content().contentType(MediaType.APPLICATION_JSON)
                        content().json(Gson().toJson(mockFolderTree))
                    }
                    .andDo(MockMvcResultHandlers.print())
                    .andDo(
                        document(
                            "find-folderTree",
                            preprocessResponse(prettyPrint())
                        )
                    )
            }
            it("if empty, should return empty list") {
                every { mockService.findAll() } returns listOf()
                mockMvc.perform(
                    RestDocumentationRequestBuilders.get(URI)
                )
                    .andExpect {
                        status().isOk
                        content().contentType(MediaType.APPLICATION_JSON)
                        content().json("[]")
                    }
                    .andDo(MockMvcResultHandlers.print())
                    .andDo(
                        document(
                            "find-folderTree",
                            preprocessResponse(prettyPrint()),
                        )
                    )
            }
        }
        context("POST /api/folder") {
            it("should return created folderTree") {
                every { mockService.create(any()) } returns mockFolderTree
                mockMvc.perform(
                    RestDocumentationRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Gson().toJson(mockFolderTree))
                )
                    .andExpect {
                        status().isCreated
                        content().contentType(MediaType.APPLICATION_JSON)
                    }
                    .andDo(MockMvcResultHandlers.print())
                    .andDo(
                        document(
                            "create-folderTree",
                            preprocessResponse(prettyPrint()),
                            requestFields(
                                fieldWithPath("name").description("folderTree name"),
                                fieldWithPath("children").description("folderTree children, it also has name and children")
                            ),
                        )
                    )
            }
            it("if request format invalid, return 400 Response code") {
                every { mockService.create(any()) } returns mockFolderTree
                mockMvc.perform(
                    RestDocumentationRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Gson().toJson(mockFolderTree))
                )
                    .andExpect {
                        status().isBadRequest
                    }
                    .andDo(MockMvcResultHandlers.print())
                    .andDo(
                        document(
                            "create-folderTree",
                            preprocessResponse(prettyPrint()),
                            requestFields(
                                fieldWithPath("name").description("folderTree name"),
                                fieldWithPath("children").description("folderTree children, it also has name and children")
                            ),
                        )
                    )
            }
            it("if creation failed, return 500 response code with error message") {
                every { mockService.create(any()) } throws Exception("error")
                mockMvc.perform(
                    RestDocumentationRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Gson().toJson(mockFolderTree))
                )
                    .andExpect {
                        status().isInternalServerError
                        content().contentType(MediaType.APPLICATION_JSON)
                        content().json("{\"message\":\"error\"}")
                    }
                    .andDo(MockMvcResultHandlers.print())
                    .andDo(
                        document(
                            "create-folderTree",
                            preprocessResponse(prettyPrint()),
                            requestFields(
                                fieldWithPath("name").description("folderTree name"),
                                fieldWithPath("children").description("folderTree children, it also has name and children")
                            ),
                            responseFields(
                                fieldWithPath("message").description("server error message")
                            )
                        )
                    )

            }
        }
        context("PUT /api/folder/{id}") {
            it("should return updated folderTree") {
                every { mockService.update(any(), any()) } returns mockFolderTree
                mockMvc.perform(
                    RestDocumentationRequestBuilders.put("$URI/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Gson().toJson(mockFolderTree))
                )
                    .andExpect {
                        status().isOk
                    }
                    .andDo(MockMvcResultHandlers.print())
                    .andDo(
                        document(
                            "update-folderTree",
                            preprocessResponse(prettyPrint()),
                            requestFields(
                                fieldWithPath("name").description("folderTree name"),
                                fieldWithPath("children").description("folderTree children, it also has name and children")
                            ),
                        )
                    )
            }
            it("if request format invalid, return 400 Response code") {
                every { mockService.update(any(), any()) } returns mockFolderTree
                mockMvc.perform(
                    RestDocumentationRequestBuilders.put("$URI/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Gson().toJson(mockFolderTree))
                )
                    .andExpect {
                        status().isBadRequest
                    }
                    .andDo(MockMvcResultHandlers.print())
                    .andDo(
                        document(
                            "update-folderTree",
                            preprocessResponse(prettyPrint()),
                            requestFields(
                                fieldWithPath("name").description("folderTree name"),
                                fieldWithPath("children").description("folderTree children, it also has name and children")
                            ),
                        )
                    )
            }
            it("if update failed, return 500 response code with error message") {
                every { mockService.update(any(), any()) } throws Exception("error")
                mockMvc.perform(
                    RestDocumentationRequestBuilders.put("$URI/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Gson().toJson(mockFolderTree))
                )
                    .andExpect {
                        status().isInternalServerError
                        content().contentType(MediaType.APPLICATION_JSON)
                        content().json("{\"message\":\"error\"}")
                    }
                    .andDo(MockMvcResultHandlers.print())
                    .andDo(
                        document(
                            "update-folderTree",
                            preprocessResponse(prettyPrint()),
                            requestFields(
                                fieldWithPath("name").description("folderTree name"),
                                fieldWithPath("children").description("folderTree children, it also has name and children")
                            ),
                            responseFields(
                                fieldWithPath("message").description("server error message")
                            )
                        )
                    )
            }
        }
        context("DELETE /api/folder/{id}") {
            it("should return 204 response code") {
                every { mockService.delete(any()) } returns Unit
                mockMvc.perform(
                    RestDocumentationRequestBuilders.delete("$URI/1")
                )
                    .andExpect {
                        status().isNoContent
                    }
                    .andDo(MockMvcResultHandlers.print())
                    .andDo(
                        document(
                            "delete-folderTree",
                            preprocessResponse(prettyPrint()),
                        )
                    )
            }
            it("if delete failed, return 500 response code with error message") {
                every { mockService.delete(any()) } throws Exception("error")
                mockMvc.perform(
                    RestDocumentationRequestBuilders.delete("$URI/1")
                )
                    .andExpect {
                        status().isInternalServerError
                        content().contentType(MediaType.APPLICATION_JSON)
                        content().json("{\"message\":\"server error message\"}")
                    }
                    .andDo(MockMvcResultHandlers.print())
                    .andDo(
                        document(
                            "delete-folderTree",
                            preprocessResponse(prettyPrint()),
                            responseFields(
                                fieldWithPath("message").description("server error message")
                            )
                        )
                    )
            }
        }
    }
})
