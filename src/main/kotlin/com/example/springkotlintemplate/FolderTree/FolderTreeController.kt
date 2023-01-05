package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeRequestDto
import com.example.springkotlintemplate.FolderTree.Entity.FolderTree

interface FolderTreeController {
    fun findAllRootFolder(): List<FolderTree>
    fun findFolderTreeById(id: Long): FolderTree?
    fun createFolderTree(folderTree: FolderTreeRequestDto)
    fun updateFolderTree(folderTree: FolderTree)
    fun deleteFolderTree(id: Long)
    fun deleteAllFolderTree()
}