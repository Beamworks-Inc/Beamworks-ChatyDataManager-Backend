package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.Dto.KeywordDto
import com.example.springkotlintemplate.Contents.Entity.Contents

interface ContentsService {
    fun findAllByFolderId(folderId: Long): List<Contents>
    fun create(content : Contents)
    fun update(targetContentsId: Long, contents: Contents)
    fun delete(targetContentsId: Long)
    fun findById(contentsId: Long): Contents
    fun uploadValidateContents()
    fun findAllKeywordList(): List<KeywordDto>
    fun findAllReviewerKeywordList(): List<KeywordDto>
    fun findAllContentsContainKeyword(keyword: List<String>): List<Contents>
    fun findAllContentsContainReviewerKeyword(keyword: List<String>): List<Contents>
    fun findAllContentsContainKeywordAndReviewerKeyword(
        keyword: List<String>,
        reviewerKeyword: List<String>,
    ): List<Contents>

    fun deleteAllContents()

}
