package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeRequestDto
import com.example.springkotlintemplate.FolderTree.Entity.FolderTree
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface FolderTreeController {
    fun findAllRootFolder(): List<FolderTree>
    fun findFolderTreeById(id: Long): FolderTree?
    fun createFolderTree(folderTree: FolderTreeRequestDto): FolderTree
    fun updateFolderTree(@PathVariable id: Long, @RequestBody folderTree: FolderTreeRequestDto): FolderTree
    fun deleteFolderTree(id: Long)
    fun deleteAllFolderTree()
}