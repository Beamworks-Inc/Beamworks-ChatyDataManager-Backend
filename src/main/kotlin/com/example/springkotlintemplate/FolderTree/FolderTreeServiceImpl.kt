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


    override fun deleteAll() {
        folderTreeRepository.deleteAll()
    }

    override fun deleteById(id: Long): FolderTree? {
        val folderTree=folderTreeRepository.findById(id).orElseThrow { FolderTreeNotFoundException() }
        return if(folderTree.parent==null){
            folderTreeRepository.deleteById(id)
            null
        } else{
            folderTree.parent.children.remove(folderTree)
            val root=folderTree.parent.getRoot()
            folderTreeRepository.save(folderTree.copy(parent = null))
            folderTreeRepository.deleteById(id)
            root
        }
    }

    override fun changeName(name: String, id: Long): FolderTree {
        val folderTree: FolderTree=folderTreeRepository.findById(id).orElseThrow { FolderTreeNotFoundException() }
        val root=folderTree.getRoot()
        folderTreeRepository.save(folderTree.copy(name = name))
        return root
    }

    override fun addChild(childName: String, parentId: Long): FolderTree {
        val parentFolderTree: FolderTree=folderTreeRepository.findById(parentId).orElseThrow { FolderTreeNotFoundException() }
        val childFolderTree=FolderTree(0,childName,parentFolderTree, mutableListOf())
        parentFolderTree.children.add(childFolderTree)
        folderTreeRepository.save(parentFolderTree)
        return parentFolderTree.getRoot()
    }

    override fun addSiblingsFromRoot(siblingNames: List<String>) {
        val root= findAllRootFolder().first()
        root.checkHasChildOrAppend(siblingNames)
        folderTreeRepository.save(root)
    }

    private fun FolderTree.checkHasChildOrAppend(siblingNames: List<String>) {
        if(siblingNames.isEmpty()){
            return
        }
        if(siblingNames[0] in children.map { it.name }){
            val child= children.first { it.name == siblingNames[0] }
            child.checkHasChildOrAppend(siblingNames.drop(1))
        } else{
            val child=FolderTree(0,siblingNames[0],this, mutableListOf())
            children.add(child)
            child.checkHasChildOrAppend(siblingNames.drop(1))
        }
    }


    private fun findRootFolder(folderId: Long): FolderTree{
        val folder = findById(folderId) ?: throw FolderTreeNotFoundException()
        return if(folder.parent == null){
            folder
        } else{
            findRootFolder(folder.parent.id)
        }
    }

    private fun FolderTree.getRoot(): FolderTree {
        if(parent == null) return this
        return parent.getRoot()
    }
}

