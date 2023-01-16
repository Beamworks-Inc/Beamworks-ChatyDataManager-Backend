package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.Entity.Contents
import com.example.springkotlintemplate.Contents.Entity.ReviewState
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ContentsRepository: CrudRepository<Contents,Long> {
    fun findAllByFolderId(folderId: Long): List<Contents>

    fun findAllByStatusIs(status: ReviewState): List<Contents>
}