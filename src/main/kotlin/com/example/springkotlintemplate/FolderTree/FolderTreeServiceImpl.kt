package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.Exception.FolderTreeAlreadyExistException
import com.example.springkotlintemplate.FolderTree.Exception.FolderTreeNotFoundException
import com.example.springkotlintemplate.FolderTree.VO.FolderTree
import org.springframework.stereotype.Service

@Service
class FolderTreeServiceImpl(
    private val folderTreeRepository: FolderTreeRepository
): FolderTreeService {
    override fun create(folderTree: FolderTree): FolderTree {
        if(folderTreeRepository.findByName(folderTree.name)!=null){
            throw FolderTreeAlreadyExistException()
        }
        return folderTreeRepository.save(folderTree)
    }

    override fun update(targetId: String, folderTree: FolderTree): FolderTree {
        folderTreeRepository.findByName(folderTree.name) ?: throw FolderTreeNotFoundException()
        return folderTreeRepository.save(folderTree)
    }

    override fun delete(id: String) {
        folderTreeRepository.findByName(id) ?: throw FolderTreeNotFoundException()
        folderTreeRepository.deleteByName(id)
    }

    override fun findAll(): List<FolderTree> {
        return folderTreeRepository.findAll().toList()
    }

    override fun findById(id: String): FolderTree? {
        return folderTreeRepository.findByName(id) ?: throw FolderTreeNotFoundException()
    }
}