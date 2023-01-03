package com.example.springkotlintemplate.FolderTree.VO

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class FolderTree(
    @Id
    val name : String,
    @OneToMany
    val children : List<FolderTree>
){
    constructor() : this("", listOf())
}
