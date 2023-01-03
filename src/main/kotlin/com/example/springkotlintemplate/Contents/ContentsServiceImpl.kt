package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.Exception.ContentsNotFoundException
import com.example.springkotlintemplate.Contents.VO.Contents
import com.example.springkotlintemplate.FolderTree.Exception.FolderTreeNotFoundException
import com.example.springkotlintemplate.FolderTree.FolderTreeService
import org.springframework.stereotype.Service

@Service
class ContentsServiceImpl(
    val contentsRepository: ContentsRepository,
    val folderTreeService: FolderTreeService) : ContentsService {
    override fun findAllByFolderName(folderName: String): List<Contents> {
        return contentsRepository.findAllByFolderName(folderName)
    }

    override fun create(content: Contents): Contents {
        folderTreeService.findById(content.folder.name)?: throw FolderTreeNotFoundException()
        return contentsRepository.save(content)
    }

    override fun update(targetContentsId: Long, contents: Contents): Contents {
        contentsRepository.findById(targetContentsId).isEmpty.let { throw ContentsNotFoundException() }
        contents.id = targetContentsId
        return contentsRepository.save(contents)
    }

    override fun delete(targetContentsId: Long) {
        contentsRepository.findById(targetContentsId).isEmpty.let { throw ContentsNotFoundException() }
        return contentsRepository.deleteById(targetContentsId)

    }
}