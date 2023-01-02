package com.example.springkotlintemplate.FolderTree.VO

data class FolderTree(
    val name : String,
    val children : List<FolderTree>
){
    constructor() : this("", listOf())
}