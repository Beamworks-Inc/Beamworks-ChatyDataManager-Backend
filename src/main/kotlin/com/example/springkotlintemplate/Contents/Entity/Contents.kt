package com.example.springkotlintemplate.Contents.Entity

import com.example.springkotlintemplate.FolderTree.Entity.FolderTree
import com.example.springkotlintemplate.User.User
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(indexes = [javax.persistence.Index(name = "IDX_FOLDER_ID", columnList = "folder_id")])
data class Contents(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(name = "folder_id")
    @JoinColumn(name = "folder_tree.id")
    val folderId : Long,
    val question : String,
    val answer : String,
    @OneToMany(cascade = [CascadeType.ALL])
    val reference : MutableList<Reference>,
    @OneToOne(cascade = [CascadeType.ALL]) val rationale : Rationale,
    val writeDate : LocalDateTime,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user.id")
    val writer : User,
    @ElementCollection val keyword : MutableList<String>,
    @OneToOne(cascade = [CascadeType.ALL])
    val review : Review,
    val state : ReviewState
){
    constructor(): this(0,
        1,
        "",
        "",
        mutableListOf(),
        Rationale(),
        LocalDateTime.now(),
        User(),
        mutableListOf(),
        Review(),
        ReviewState.DRAFT
    )
}
