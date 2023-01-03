package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.VO.FolderTree

interface FolderTreeService {
    fun create(folderTree: FolderTree) : FolderTree
    fun update(targetId : String,folderTree: FolderTree): FolderTree
    fun delete(id: String)
    fun findAll(): List<FolderTree>
    fun findById(id: String): FolderTree?

}
