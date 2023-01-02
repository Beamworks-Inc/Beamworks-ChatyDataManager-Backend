package com.example.springkotlintemplate.FolderTree


interface FolderTreeRepository {
    fun findByName(name: String) : FolderTree?

    fun findAll(): List<FolderTree>

    fun save(folderTree: FolderTree): FolderTree
    fun delete(name: String)

}
