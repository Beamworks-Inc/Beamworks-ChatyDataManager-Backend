package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.Dto.KeywordDto
import com.example.springkotlintemplate.Contents.Exception.ContentsNotFoundException
import com.example.springkotlintemplate.Contents.Entity.Contents
import com.example.springkotlintemplate.Contents.Entity.ReviewState
import com.example.springkotlintemplate.FolderTree.FolderTreeService
import com.example.springkotlintemplate.Lex.Dto.LexUpdateDto
import com.example.springkotlintemplate.Lex.LexService
import org.springframework.stereotype.Service

@Service
class ContentsServiceImpl(
    val contentsRepository: ContentsRepository,
    val folderTreeService: FolderTreeService,
    val lexService: LexService
) : ContentsService {

    override fun findAllByFolderId(folderId: Long): List<Contents> {
        return contentsRepository.findAllByFolderId(folderId)
    }

    override fun create(content: Contents) {
        checkIsValidContent(content)
        contentsRepository.save(content)
    }

    private fun checkIsValidContent(content: Contents) {
        if(content.answer.isEmpty() || content.question.isEmpty()){
            throw IllegalArgumentException("answer or question is empty")
        }
    }

    override fun update(targetContentsId: Long, contents: Contents) {
        if(contentsRepository.findById(targetContentsId).isEmpty){
            throw ContentsNotFoundException()
        }
        contentsRepository.save(contents)
    }

    override fun delete(targetContentsId: Long) {
        if(contentsRepository.findById(targetContentsId).isEmpty){
            throw ContentsNotFoundException()
        }
        return contentsRepository.deleteById(targetContentsId)

    }

    override fun findById(contentsId: Long): Contents {
        return contentsRepository.findById(contentsId).orElseThrow { ContentsNotFoundException() }
    }

    override fun uploadValidateContents() {
        lexService.updateQnA(getValidateContents().map { LexUpdateDto(it.id,it.question,it.answer) })
    }

    override fun findAllKeywordList(): List<KeywordDto> {
        return contentsRepository.findAllKeywordList().groupBy { it }
            .map { KeywordDto(it.key, it.value.size) }
    }

    override fun findAllReviewerKeywordList(): List<KeywordDto> {
        return contentsRepository.findAllReviewerKeywordList().groupBy { it }
            .map { KeywordDto(it.key, it.value.size) }
    }

    override fun findAllContentsContainKeyword(keyword: List<String>): List<Contents> {
        return contentsRepository.findDistinctByKeywordIn(keyword).filter {
            it.keyword.containsAll(keyword)
        }
    }

    override fun findAllContentsContainReviewerKeyword(keyword: List<String>): List<Contents> {
        return contentsRepository.findDistinctByReviewerKeywordIn(keyword)
    }

    override fun findAllContentsContainKeywordAndReviewerKeyword(
        keyword: List<String>,
        reviewerKeyword: List<String>,
    ): List<Contents> {
        return contentsRepository.findDistinctByKeywordIn(keyword).filter {
            it.keyword.containsAll(keyword)
        }.filter {content ->
            reviewerKeyword.any { it == content.reviewerKeyword }
        }
    }

    override fun deleteAllContents() {
        contentsRepository.deleteAll()
    }

    private fun getValidateContents(): List<Contents> {
        return contentsRepository.findAllByStatusIs(ReviewState.APPROVED)
    }
}