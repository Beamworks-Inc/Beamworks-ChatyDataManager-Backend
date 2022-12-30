package com.example.springkotlintemplate.FolderTree

import org.springframework.stereotype.Service

@Service
class FolderTreeServiceImpl(
    private val folderTreeRepository: FolderTreeRepository
): FolderTreeService {
    override fun create(folderTree: FolderTree): FolderTree {
        TODO("Not yet implemented")
    }

    override fun update(targetId: String, folderTree: FolderTree): FolderTree {
        TODO("Not yet implemented")
    }

    override fun delete(any: Any) {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<FolderTree> {
        TODO("Not yet implemented")
    }

    override fun findById(any: Any): FolderTree {
        TODO("Not yet implemented")
    }
}