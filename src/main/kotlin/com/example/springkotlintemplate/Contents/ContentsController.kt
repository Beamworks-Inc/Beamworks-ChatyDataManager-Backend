package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.Entity.Contents

interface ContentsController {
    fun create(contents : Contents)
    fun update(contents : Contents)
    fun delete(contentsId : Long)
    fun findAllByFolderId(folderId : Long): List<Contents>
    fun findById(contentsId : Long): Contents
    fun uploadValidateContents()

}