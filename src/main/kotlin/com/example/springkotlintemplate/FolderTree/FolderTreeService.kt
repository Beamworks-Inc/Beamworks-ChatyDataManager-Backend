package com.example.springkotlintemplate.FolderTree

interface FolderTreeService {
    fun create(folderTree: FolderTree) : FolderTree
    fun update(targetId : String,folderTree: FolderTree): FolderTree
    fun delete(any: Any)
    fun findAll(): List<FolderTree>
    fun findById(any: Any): FolderTree

}
