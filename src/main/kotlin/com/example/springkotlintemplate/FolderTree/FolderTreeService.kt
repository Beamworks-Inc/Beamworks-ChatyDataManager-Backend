package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeRequestDto
import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeResponseDto
import com.example.springkotlintemplate.FolderTree.Entity.FolderTree

interface FolderTreeService {
    fun create(folderTree: FolderTreeRequestDto) : FolderTreeResponseDto
    fun update(targetId : String,folderTree: FolderTreeRequestDto): FolderTreeResponseDto
    fun delete(id: String)
    fun findAll(): List<FolderTreeResponseDto>
    fun findById(id: String): FolderTreeResponseDto?

}
