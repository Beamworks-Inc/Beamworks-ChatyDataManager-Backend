package com.example.springkotlintemplate.FolderTree.Entity

import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeResponseDto
import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
data class FolderTree(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    val name : String,
    @JsonBackReference
    @OneToOne(cascade = [CascadeType.ALL])
    val parent : FolderTree?,
    @OneToMany(cascade = [CascadeType.ALL])
    var children : MutableList<FolderTree>
){
    constructor() : this(1,"", null,mutableListOf())

}

