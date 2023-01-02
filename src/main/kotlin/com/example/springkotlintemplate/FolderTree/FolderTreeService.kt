package com.example.springkotlintemplate.FolderTree

interface FolderTreeService {
    fun create(folderTree: FolderTree) : FolderTree
    fun update(targetId : String,folderTree: FolderTree): FolderTree
    fun delete(id: String)
    fun findAll(): List<FolderTree>
    fun findById(id: String): FolderTree?

}
