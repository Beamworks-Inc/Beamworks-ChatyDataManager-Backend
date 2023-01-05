package com.example.springkotlintemplate.Contents.Entity

import com.example.springkotlintemplate.FolderTree.Entity.FolderTree
import com.example.springkotlintemplate.User.User
import java.util.*
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(indexes = [javax.persistence.Index(name = "IDX_FOLDER_ID", columnList = "folder_id")])
data class Contents(
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    val id: Long,
    @Column(name = "folder_id")
    @JoinColumn(name = "folder_tree.id")
    val folderId : Long,
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
        1,
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

