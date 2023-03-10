package com.example.springkotlintemplate.Contents.Dto

import com.example.springkotlintemplate.Contents.Entity.Reference
import com.example.springkotlintemplate.Contents.Entity.Review
import com.example.springkotlintemplate.Contents.Entity.ReviewState
import com.example.springkotlintemplate.User.Entity.User
import java.time.LocalDateTime

data class ContentsRequestDto (
    val id: Long,
    val folderId : Long,
    val question : String,
    val answer : String,
    val reference : MutableList<Reference>,
    val writeDate : LocalDateTime,
    val writer : User,
    val review : Review,
    val status : ReviewState
){
    constructor(): this(0,
        1,
        "",
        "",
        mutableListOf(),
        LocalDateTime.now(),
        User(),
        Review(),
        ReviewState.DRAFT
    )
}