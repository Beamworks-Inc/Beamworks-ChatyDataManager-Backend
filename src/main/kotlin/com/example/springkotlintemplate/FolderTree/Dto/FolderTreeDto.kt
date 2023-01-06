package com.example.springkotlintemplate.FolderTree.Dto

import com.example.springkotlintemplate.FolderTree.Entity.FolderTree

data class FolderTreeRequestDto (
    val name : String,
    val children : List<FolderTreeRequestDto>
){
    constructor() : this("", mutableListOf())

    fun toFolderTreeEntity(parentFolder : FolderTree?=null): FolderTree {
        if(children.isEmpty()){
            return FolderTree(1,name,parentFolder,mutableListOf())
        }
        val folderTree=FolderTree(1,name,parentFolder,mutableListOf())
        folderTree.children=children.map { children->
            children.toFolderTreeEntity(folderTree)
        }.toMutableList()
        return folderTree
    }
}

data class FolderTreeResponseDto (
    val name : String,
    val children : MutableList<FolderTreeResponseDto>
){
    constructor() : this("", mutableListOf())
}