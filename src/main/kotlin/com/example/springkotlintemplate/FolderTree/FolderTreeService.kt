package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeRequestDto
import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeResponseDto

interface FolderTreeService {
    fun create(folderTree: FolderTreeRequestDto) : FolderTreeResponseDto
    fun update(targetId : String,folderTree: FolderTreeRequestDto): FolderTreeResponseDto
    fun delete(id: String)
    fun findAllRootFolder(): List<FolderTreeResponseDto>
    fun findByName(id: String): FolderTreeResponseDto?
    fun deleteAll()
    fun findById(folderId: Long): FolderTreeResponseDto?

}
