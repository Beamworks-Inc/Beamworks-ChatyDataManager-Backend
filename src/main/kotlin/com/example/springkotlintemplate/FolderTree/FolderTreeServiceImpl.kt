package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeRequestDto
import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeResponseDto
import com.example.springkotlintemplate.FolderTree.Exception.FolderTreeAlreadyExistException
import com.example.springkotlintemplate.FolderTree.Exception.FolderTreeNotFoundException
import com.example.springkotlintemplate.FolderTree.Entity.FolderTree
import org.springframework.stereotype.Service

@Service
class FolderTreeServiceImpl(
    private val folderTreeRepository: FolderTreeRepository
): FolderTreeService {
    override fun create(folderTreeRequestDto: FolderTreeRequestDto): FolderTreeResponseDto {
        val folderTree=folderTreeRequestDto.toFolderTreeEntity()
        if(folderTreeRepository.findByName(folderTree.name)!=null){
            throw FolderTreeAlreadyExistException()
        }
        return folderTreeRepository.save(folderTree).toFolderTreeResponse()
    }

    override fun update(targetId: String, folderTreeRequestDto: FolderTreeRequestDto): FolderTreeResponseDto {
        val targetFolderTree=folderTreeRepository.findByName(targetId)?: throw FolderTreeNotFoundException()
        val folderTree=FolderTree(
            targetFolderTree.id,
            folderTreeRequestDto.name,
            targetFolderTree.parent,
            folderTreeRequestDto.children.map { it.toFolderTreeEntity() }.toMutableList()
        )
        return folderTreeRepository.save(folderTree).toFolderTreeResponse()
    }

    override fun delete(id: String) {
        folderTreeRepository.findByName(id) ?: throw FolderTreeNotFoundException()
        folderTreeRepository.deleteByName(id)
    }

    override fun findAllRootFolder(): List<FolderTreeResponseDto> {
        return folderTreeRepository.findAllByParentIsNull()
            .map { it.toFolderTreeResponse() }
            .toList()
    }

    override fun findById(id: String): FolderTreeResponseDto? {
        val result=folderTreeRepository.findByName(id) ?: throw FolderTreeNotFoundException()
        return result.toFolderTreeResponse()
    }

    override fun deleteAll() {
        folderTreeRepository.deleteAll()
    }
}