package com.example.springkotlintemplate

import com.example.springkotlintemplate.Config.RestExceptionHandler.RestControllerAdviceConfig
import com.example.springkotlintemplate.Contents.ContentsControllerImpl
import com.example.springkotlintemplate.Contents.ContentsService
import com.example.springkotlintemplate.FolderTree.FolderTreeController
import com.example.springkotlintemplate.FolderTree.FolderTreeService
import io.mockk.mockk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder


@TestConfiguration
class MockMvcConfig {
    private val restDocumentation = ManualRestDocumentation()
    @Bean
    fun getMockMvc(): MockMvc =
        MockMvcBuilders.standaloneSetup(
                FolderTreeController(getMockFolderService()),
                ContentsControllerImpl(getMockContentService())
            ).setControllerAdvice(RestControllerAdviceConfig())
            .apply<StandaloneMockMvcBuilder>(documentationConfiguration(restDocumentation))
            .build()

    @Bean
    fun getMockContentService(): ContentsService=mockk<ContentsService>()
    @Bean
    fun getMockFolderService(): FolderTreeService=mockk<FolderTreeService>()
    @Bean
    fun getRestDocumentation(): ManualRestDocumentation = restDocumentation

}