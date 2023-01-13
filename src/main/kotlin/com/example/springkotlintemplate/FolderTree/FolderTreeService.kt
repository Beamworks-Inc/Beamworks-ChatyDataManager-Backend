package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeRequestDto
import com.example.springkotlintemplate.FolderTree.Entity.FolderTree

interface FolderTreeService {
    fun findAllRootFolder(): List<FolderTree>
    fun findById(folderId: Long): FolderTree?
    fun create(folderTree: FolderTreeRequestDto): FolderTree
    fun deleteAll()
    fun deleteById(id: Long): FolderTree?
    fun changeName(name: String, id: Long): FolderTree
    fun addChild(childName: String, parentId: Long): FolderTree

}
