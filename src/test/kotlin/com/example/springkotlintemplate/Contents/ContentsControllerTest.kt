package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.Exception.ContentsNotFoundException
import com.example.springkotlintemplate.Contents.VO.Contents
import com.example.springkotlintemplate.Config.RestExceptionHandler.RestControllerAdviceConfig
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class ContentsControllerTest :DescribeSpec({
    val mockContentsService = mockk<ContentsService>()
    val mockMvc= MockMvcBuilders
        .standaloneSetup(ContentsController(mockContentsService))
        .setControllerAdvice(RestControllerAdviceConfig())
        .build()
    val mockContents= Contents()
    val jsonMockContents= ObjectMapper().writeValueAsString(mockContents)
    val URI="/api/v1/contents"
    describe("ContentsController") {
        context("GET $URI/{folderName}") {
            every { mockContentsService.findAllByFolderName(any()) } returns listOf(mockContents)
            it("폴더에 포함된 모든 컨텐츠 리스트를 반환해야한다.") {
                mockMvc.get("$URI/1"){
                    accept = MediaType.APPLICATION_JSON
                }.andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    content { mockContents }
                }
            }
        }
        context("POST $URI") {
            it("컨텐츠를 만들고 결과를 반환해야한다.") {
                every { mockContentsService.create(any()) } returns mockContents
                mockMvc.post(URI) {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON_UTF8
                    content = jsonMockContents
                }.andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    content { string(jsonMockContents) }
                }
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
                mockMvc.put("$URI/1") {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                    content = jsonMockContents
                }.andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    content { string(jsonMockContents) }
                }
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
            every { mockContentsService.delete( any()) } returns mockContents
            it("컨텐츠를 삭제하고 결과를 반환해야한다.") {
                mockMvc.delete("$URI/1") .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    content { string(jsonMockContents) }
                }
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
