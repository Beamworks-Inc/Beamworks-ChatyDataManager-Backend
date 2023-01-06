package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.Exception.ContentsNotFoundException
import com.example.springkotlintemplate.Contents.Entity.Contents
import com.example.springkotlintemplate.FolderTree.Exception.FolderTreeNotFoundException
import com.example.springkotlintemplate.FolderTree.FolderTreeService
import org.springframework.stereotype.Service

@Service
class ContentsServiceImpl(
    val contentsRepository: ContentsRepository,
    val folderTreeService: FolderTreeService) : ContentsService {

    override fun findAllByFolderId(folderId: Long): List<Contents> {
        return contentsRepository.findAllByFolderId(folderId)
    }

    override fun create(content: Contents) {
        folderTreeService.findById(content.folderId)?: throw FolderTreeNotFoundException()
        contentsRepository.save(content)
    }

    override fun update(targetContentsId: Long, contents: Contents) {
        if(contentsRepository.findById(targetContentsId).isEmpty){
            throw ContentsNotFoundException()
        }
        contentsRepository.save(contents)
    }

    override fun delete(targetContentsId: Long) {
        if(contentsRepository.findById(targetContentsId).isEmpty){
            throw ContentsNotFoundException()
        }
        return contentsRepository.deleteById(targetContentsId)

    }

    override fun findById(contentsId: Long): Contents {
        return contentsRepository.findById(contentsId).orElseThrow { ContentsNotFoundException() }
    }
}