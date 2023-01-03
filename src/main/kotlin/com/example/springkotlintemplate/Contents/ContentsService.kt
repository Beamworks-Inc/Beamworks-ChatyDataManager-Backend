package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.VO.Contents

interface ContentsService {
    fun findAllByFolderName(folderName: String): List<Contents>
    fun create(content : Contents): Contents
    fun update(targetContentsId: Long, contents: Contents) : Contents
    fun delete(targetContentsId: Long) : Contents
}
