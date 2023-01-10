package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.Entity.Contents

interface ContentsService {
    fun findAllByFolderId(folderId: Long): List<Contents>
    fun create(content : Contents)
    fun update(targetContentsId: Long, contents: Contents)
    fun delete(targetContentsId: Long)
    fun findById(contentsId: Long): Contents

}
