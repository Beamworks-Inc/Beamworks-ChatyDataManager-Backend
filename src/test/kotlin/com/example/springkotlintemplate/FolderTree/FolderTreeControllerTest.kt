package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.RestExceptionHandler.RestControllerAdviceConfig
import com.google.gson.Gson
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class FolderTreeControllerTest: DescribeSpec({
    val mockService = mockk<FolderTreeService>()
    val mockMvc= MockMvcBuilders
        .standaloneSetup(FolderTreeController(mockService))
        .setControllerAdvice(RestControllerAdviceConfig())
        .build()
    val URI="/api/v1/folder"
    val mockFolderTree = FolderTree("root", listOf(
        FolderTree("child1", listOf(
            FolderTree("grandchild1", listOf()),
            FolderTree("grandchild2", listOf())
        )),
        FolderTree("child2", listOf())
    ))
    describe("FolderTreeController") {
        context("GET /api/folder") {
            it("모든 폴더 트리 리스트를 반환해야한다.") {
                every { mockService.findAll() } returns listOf(mockFolderTree)
                mockMvc.get(URI) {
                    accept = MediaType.APPLICATION_JSON
                }.andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    content { string(Gson().toJson(listOf(mockFolderTree))) }
                }
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
                every { mockService.create(any()) } returns mockFolderTree
                mockMvc.post(URI) {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                    content = Gson().toJson(mockFolderTree)
                }.andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    content { string(Gson().toJson(mockFolderTree)) }
                }
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
                every { mockService.update(any(), any()) } returns mockFolderTree
                mockMvc.put("$URI/1") {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                    content = Gson().toJson(mockFolderTree)
                }.andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    content { string(Gson().toJson(mockFolderTree)) }
                }
            }
            it("폴더 트리 요청 형식이 잘못되었다면, 400 에러와 형식이 잘못되었다는 메시지를 반환해야한다.") {
                every { mockService.update(any(), any()) } returns mockFolderTree
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
                mockMvc.delete("$URI/1") {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                }.andExpect {
                    status { isOk() }
                }
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
