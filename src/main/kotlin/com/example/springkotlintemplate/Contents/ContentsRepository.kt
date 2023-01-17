package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.Entity.Contents
import com.example.springkotlintemplate.Contents.Entity.ReviewState
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ContentsRepository: CrudRepository<Contents,Long> {
    fun findAllByFolderId(folderId: Long): List<Contents>

    fun findAllByStatusIs(status: ReviewState): List<Contents>

    @Query(value = "SELECT keyword FROM contents_keyword", nativeQuery = true)
    fun findAllKeywordList(): List<String>

    @Query(value = "SELECT reviewer_keyword FROM contents", nativeQuery = true)
    fun findAllReviewerKeywordList(): List<String>

    fun findDistinctByKeywordIn(keyword: List<String>): List<Contents>
    fun findDistinctByReviewerKeywordIn(reviewerKeyword: List<String>): List<Contents>


}