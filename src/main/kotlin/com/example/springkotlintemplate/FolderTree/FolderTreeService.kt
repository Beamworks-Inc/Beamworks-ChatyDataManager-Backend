package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeRequestDto
import com.example.springkotlintemplate.FolderTree.Entity.FolderTree

interface FolderTreeService {
    fun findAllRootFolder(): List<FolderTree>
    fun findById(folderId: Long): FolderTree?
    fun create(folderTree: FolderTreeRequestDto)
    fun update(folderTree: FolderTree)
    fun deleteAll()
    fun deleteById(id: Long)

}
