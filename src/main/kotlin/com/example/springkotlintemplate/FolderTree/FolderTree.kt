package com.example.springkotlintemplate.FolderTree

data class FolderTree(
    val name : String,
    val children : List<FolderTree>
)
