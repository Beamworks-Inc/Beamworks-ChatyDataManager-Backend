package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeRequestDto
import com.example.springkotlintemplate.FolderTree.Entity.FolderTree
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface FolderTreeController {
    fun findAllRootFolder(): List<FolderTree>
    fun findFolderTreeById(id: Long): FolderTree?
    fun createFolderTree(folderTree: FolderTreeRequestDto): FolderTree
    fun changeName(@PathVariable id: Long, @PathVariable name: String): FolderTree
    fun addChild(@PathVariable parentId : Long, @PathVariable childName : String): FolderTree

    /**
     * 하위 폴더 트리도 모두 삭제하도록 함
     */
    fun deleteFolderTree(id: Long)
    fun deleteAllFolderTree()
}