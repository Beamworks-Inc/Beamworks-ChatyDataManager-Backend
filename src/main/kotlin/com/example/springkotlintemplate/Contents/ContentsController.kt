package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.VO.Contents
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/contents")
class ContentsController(
    val contentsService: ContentsService
) {
    @GetMapping("/{folderName}")
    fun findAllByFolderName( @PathVariable folderName: String) : List<Contents> =
        contentsService.findAllByFolderName(folderName)

    @PostMapping("")
    fun create(@RequestBody contents : Contents) : Contents =
        contentsService.create(contents)

    @PutMapping("/{contentsId}")
    fun update(@PathVariable contentsId: Long, @RequestBody contents: Contents) : Contents=
        contentsService.update(contentsId, contents)

    @DeleteMapping("/{contentsId}")
    fun delete(@PathVariable contentsId: Long): Contents =
        contentsService.delete(contentsId)

}
