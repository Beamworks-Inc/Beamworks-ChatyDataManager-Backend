package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.Entity.FolderTree
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FolderTreeRepository: CrudRepository<FolderTree,Long> {
    fun findByName(name: String) : FolderTree?
    fun deleteByName(name : String)
    fun findAllByParentIsNull() : List<FolderTree>
}
