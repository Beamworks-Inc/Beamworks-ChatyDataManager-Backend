package com.example.springkotlintemplate

import com.example.springkotlintemplate.FolderTree.FolderTreeService
import com.google.gson.Gson
import io.mockk.mockk
import org.springframework.context.annotation.Bean

@org.springframework.boot.test.context.TestConfiguration
class TestConfiguration {
    @Bean
    fun gson() = Gson()
    @Bean
    fun folderTreeService() = mockk<FolderTreeService>()
}