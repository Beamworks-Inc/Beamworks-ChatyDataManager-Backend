package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.VO.Contents
import org.springframework.stereotype.Service

@Service
class ContentsServiceImpl: ContentsService {
    override fun findAllByFolderName(folderName: String): List<Contents> {
        TODO("Not yet implemented")
    }

    override fun create(content: Contents): Contents {
        TODO("Not yet implemented")
    }

    override fun update(targetContentsId: Long, contents: Contents): Contents {
        TODO("Not yet implemented")
    }

    override fun delete(targetContentsId: Long): Contents {
        TODO("Not yet implemented")
    }
}