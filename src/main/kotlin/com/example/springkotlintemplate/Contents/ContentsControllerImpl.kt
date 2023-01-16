package com.example.springkotlintemplate.Contents

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

}
