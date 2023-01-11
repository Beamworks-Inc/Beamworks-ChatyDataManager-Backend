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

    override fun create(folderTreeDto: FolderTreeRequestDto): FolderTree {
        val folderTree = folderTreeDto.toFolderTreeEntity()
        return folderTreeRepository.save(folderTree)
    }

    override fun update(folderTreeId: Long, folderTreeRequestDto: FolderTreeRequestDto): FolderTree {
        val targetFolder=findById(folderTreeId) ?:throw FolderTreeNotFoundException()
        val folderTree = folderTreeRequestDto.toFolderTreeEntity().copy(
            id = targetFolder.id,
            parent = targetFolder.parent
        )
        folderTreeRepository.save(folderTree)
        return findRootFolder(folderTreeId)
    }

    override fun deleteAll() {
        folderTreeRepository.deleteAll()
    }

    override fun deleteById(id: Long) {
        folderTreeRepository.deleteById(id)
    }


    private fun findRootFolder(folderId: Long): FolderTree{
        val folder = findById(folderId) ?: throw FolderTreeNotFoundException()
        return if(folder.parent == null){
            folder
        } else{
            findRootFolder(folder.parent.id)
        }
    }
}