package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.Exception.ContentsNotFoundException
import com.example.springkotlintemplate.Contents.VO.Contents
import com.example.springkotlintemplate.FolderTree.Exception.FolderTreeNotFoundException
import com.example.springkotlintemplate.FolderTree.FolderTreeService
import org.springframework.stereotype.Service

@Service
class ContentsServiceImpl(
    val mockContentsRepository: ContentsRepository,
    val mockFolderTreeService: FolderTreeService) : ContentsService {
    override fun findAllByFolderName(folderName: String): List<Contents> {
        return mockContentsRepository.findAllByFolderName(folderName)
    }

    override fun create(content: Contents): Contents {
        mockFolderTreeService.findById(content.folder.name)?: throw FolderTreeNotFoundException()
        return mockContentsRepository.create(content) ?: throw Exception("Contents create failed")
    }

    override fun update(targetContentsId: Long, contents: Contents): Contents {
        mockContentsRepository.findById(targetContentsId) ?: throw ContentsNotFoundException()
        return mockContentsRepository.update(targetContentsId, contents) ?: throw Exception("Contents update failed")
    }

    override fun delete(targetContentsId: Long): Contents {
        mockContentsRepository.findById(targetContentsId) ?: throw ContentsNotFoundException()
        return mockContentsRepository.delete(targetContentsId) ?: throw Exception("Contents delete failed")

    }
}