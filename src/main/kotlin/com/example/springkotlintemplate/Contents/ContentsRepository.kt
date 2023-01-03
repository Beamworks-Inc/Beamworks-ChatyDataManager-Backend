package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.VO.Contents
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ContentsRepository: CrudRepository<Contents,Long> {
    fun findAllByFolderName(folderName : String) : List<Contents>
}