package com.example.springkotlintemplate.Contents.VO

import com.example.springkotlintemplate.FolderTree.Entity.FolderTree
import com.example.springkotlintemplate.User.User
import java.util.*
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class Contents(
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    val id: Long,
    @OneToOne val folder : FolderTree,
    val question : String,
    val answer : String,
    @ElementCollection val reference : List<Reference>,
    @OneToOne val rationale : Rationale,
    val writeDate : Date,
    @OneToOne val writer : User,
    @ElementCollection val keyword : List<String>,
    @OneToOne val review : Review,
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

