package com.example.springkotlintemplate.FolderTree.Entity

import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeResponseDto
import javax.persistence.*

@Entity
data class FolderTree(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name : String,
    @OneToOne(cascade = [javax.persistence.CascadeType.ALL])
    val parent : FolderTree?,
    @OneToMany(cascade = [javax.persistence.CascadeType.ALL])
    var children : MutableList<FolderTree>
){
    constructor() : this(1,"", null,mutableListOf())

    fun toFolderTreeResponse(): FolderTreeResponseDto{
        if (children.isEmpty()){
            return FolderTreeResponseDto(name, mutableListOf())
        }
        return FolderTreeResponseDto(name,children.map { children->
            children.toFolderTreeResponse()
        }.toMutableList())
    }
}

