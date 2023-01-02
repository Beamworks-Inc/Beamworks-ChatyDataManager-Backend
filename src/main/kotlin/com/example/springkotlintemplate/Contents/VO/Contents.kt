package com.example.springkotlintemplate.Contents.VO

import com.example.springkotlintemplate.FolderTree.VO.FolderTree
import com.example.springkotlintemplate.User.User
import java.util.*

data class Contents(
    val id: Long,
    val folder : FolderTree,
    val question : String,
    val answer : String,
    val reference : List<Reference>,
    val rationale : Rationale,
    val writeDate : Date,
    val writer : User,
    val keyword : List<String>,
    val review : Review,
    val state : ReviewState
){
    constructor(): this(0,
        FolderTree(),
        "",
        "",
        listOf(),
        Rationale(),
        Date(),
        User(),
        listOf(),
        Review(),
        ReviewState.DRAFT
    )
}

