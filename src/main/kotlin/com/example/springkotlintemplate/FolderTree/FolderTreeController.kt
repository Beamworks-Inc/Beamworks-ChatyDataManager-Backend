package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeRequestDto
import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeResponseDto
import com.example.springkotlintemplate.FolderTree.Entity.FolderTree
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/folder")
class FolderTreeController(private val mockService: FolderTreeService) {
    @GetMapping("")
    fun findAllFolderTree(): List<FolderTreeResponseDto> {
        return mockService.findAll()
    }

    @GetMapping("/{id}")
    fun findFolderTreeById( @PathVariable id: String): FolderTreeResponseDto? {
        return mockService.findById(id)
    }

    @PostMapping("",  produces = ["application/json"], consumes = ["application/json"])
    fun createFolderTree(@RequestBody folderTree: FolderTreeRequestDto): FolderTreeResponseDto {
        return mockService.create(folderTree)
    }

    @PutMapping("/{rootName}",  produces = ["application/json"], consumes = ["application/json"])
    fun updateFolderTree(@PathVariable rootName: String, @RequestBody folderTree: FolderTreeRequestDto): FolderTreeResponseDto {
        return mockService.update(rootName, folderTree)
    }

    @DeleteMapping("/{id}", produces = ["application/json"])
    fun deleteFolderTree(@PathVariable id: String) {
        return mockService.delete(id)
    }

}
