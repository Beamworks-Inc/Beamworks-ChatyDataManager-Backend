package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeRequestDto
import com.example.springkotlintemplate.FolderTree.Entity.FolderTree
import com.example.springkotlintemplate.FolderTree.Exception.FolderTreeNotFoundException
import org.springframework.stereotype.Service

@Service
class FolderTreeServiceImpl(
    private val folderTreeRepository: FolderTreeRepository
): FolderTreeService {
    override fun findAllRootFolder(): List<FolderTree> {
        return folderTreeRepository.findAllByParentIsNull()
    }

    override fun findById(folderId: Long): FolderTree? {
        return folderTreeRepository.findById(folderId).orElse(null)
    }

    override fun create(folderTree: FolderTreeRequestDto) {
        folderTreeRepository.save(folderTree.toFolderTreeEntity())
    }

    override fun update(folderTreeId: Long, folderTreeRequestDto: FolderTreeRequestDto) {
        val targetFolder=findById(folderTreeId) ?:throw FolderTreeNotFoundException()
        val folderTree = folderTreeRequestDto.toFolderTreeEntity().copy(
            id = targetFolder.id,
            parent = targetFolder.parent
        )
        folderTreeRepository.save(folderTree)
    }

    override fun deleteAll() {
        folderTreeRepository.deleteAll()
    }

    override fun deleteById(id: Long) {
        folderTreeRepository.deleteById(id)
    }

}