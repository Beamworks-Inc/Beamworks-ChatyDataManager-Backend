package com.example.springkotlintemplate.Contents.Entity

import com.example.springkotlintemplate.User.Entity.User
import java.time.LocalDateTime
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
    @Column(length = 1000)
    val question : String,
    @Column(length = 2000)
    val answer : String,
    @OneToMany(cascade = [CascadeType.ALL])
    val reference : MutableList<Reference>,
    @OneToOne(cascade = [CascadeType.ALL])
    val rationale : Rationale,
    val writeDate : LocalDateTime,
    @OneToOne(cascade = [CascadeType.DETACH])
    @JoinColumn(name = "user.id")
    val writer : User,
    @ElementCollection
    val keyword : MutableList<String>,
    val reviewerKeyword : String,
    @OneToOne(cascade = [CascadeType.ALL])
    val review : Review,
    val status : ReviewState
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
        "",
        Review(),
        ReviewState.DRAFT
    )
}

