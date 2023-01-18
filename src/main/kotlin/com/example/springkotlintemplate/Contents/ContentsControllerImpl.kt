package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.Dto.KeywordDto
import com.example.springkotlintemplate.Contents.Entity.Contents
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/contents")
class ContentsControllerImpl(
    val contentsService: ContentsService
) : ContentsController {
    @GetMapping("/findAllByFolderId/{folderId}")
    override fun findAllByFolderId( @PathVariable folderId: Long): List<Contents> {
        return contentsService.findAllByFolderId(folderId)
    }
    @GetMapping("/findByContentsId/{contentsId}")
    override fun findById(  @PathVariable contentsId: Long): Contents {
        return contentsService.findById(contentsId)
    }

    @PostMapping("/updateApprovedDataToChatBot")
    override fun uploadValidateContents() {
        contentsService.uploadValidateContents()
    }

    @GetMapping("/findAllKeywordList")
    override fun findAllKeywordList(): List<KeywordDto> {
        return contentsService.findAllKeywordList()
    }

    @GetMapping("/findAllReviewerKeywordList")
    override fun findAllReviewerKeywordList(): List<KeywordDto>{
        return contentsService.findAllReviewerKeywordList()
    }
    @PostMapping("/findAllContentsContainKeyword")
    override fun findAllContentsContainKeyword(@RequestBody keyword: List<String>): List<Contents> {
        return contentsService.findAllContentsContainKeyword(keyword)
    }
    @PostMapping("/findAllContentsContainReviewerKeyword")
    override fun findAllContentsContainReviewerKeyword(@RequestBody keyword: List<String>): List<Contents> {
        return contentsService.findAllContentsContainReviewerKeyword(keyword)
    }

    @GetMapping("/findAllContentsContainKeywordAndReviewerKeyword")
    override fun findAllContentsContainKeywordAndReviewerKeyword(
        @RequestBody keyword: List<String>,
        @RequestBody reviewerKeyword: List<String>,
    ): List<Contents> {
        return contentsService.findAllContentsContainKeywordAndReviewerKeyword(keyword, reviewerKeyword)
    }


    @PostMapping("")
    override fun create(@RequestBody contents: Contents) {
        contentsService.create(contents)
    }
    @PutMapping("")
    override fun update(@RequestBody contents: Contents) {
        contentsService.create(contents)
    }
    @DeleteMapping("/{contentsId}")
    override fun delete(@PathVariable contentsId: Long) {
        contentsService.delete(contentsId)
    }

    @DeleteMapping("/deleteAllContents")
    override fun deleteAllContents() {
        contentsService.deleteAllContents()
    }

}
