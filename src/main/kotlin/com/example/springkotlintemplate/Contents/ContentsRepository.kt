package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.VO.Contents

interface ContentsRepository {
    fun findAllByFolderName(folderName : String) : List<Contents>
    fun findById(id : Long) : Contents?
    fun create(mockContents: Contents): Contents?
    fun update(id: Long, mockContents: Contents): Contents?
    fun delete(targetContentsId: Long): Contents?
}