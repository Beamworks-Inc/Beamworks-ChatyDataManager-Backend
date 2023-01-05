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
        val folderTree=folderTreeRequestDto.toFolderTreeEntity()
        folderTreeRepository.findByName(folderTree.name) ?: throw FolderTreeNotFoundException()
        return folderTreeRepository.save(folderTree).toFolderTreeResponse()
    }

    override fun delete(id: String) {
        folderTreeRepository.findByName(id) ?: throw FolderTreeNotFoundException()
        folderTreeRepository.deleteByName(id)
    }

    override fun findAll(): List<FolderTreeResponseDto> {
        return folderTreeRepository.findAll().map { it.toFolderTreeResponse() }.toList()
    }

    override fun findById(id: String): FolderTreeResponseDto? {
        val result=folderTreeRepository.findByName(id) ?: throw FolderTreeNotFoundException()
        return result.toFolderTreeResponse()
    }
}