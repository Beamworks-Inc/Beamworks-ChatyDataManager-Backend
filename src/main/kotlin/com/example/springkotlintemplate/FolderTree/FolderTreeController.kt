package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.VO.FolderTree
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/folder")
class FolderTreeController(private val mockService: FolderTreeService) {
    @GetMapping("")
    fun findAllFolderTree(): List<FolderTree> {
        return mockService.findAll()
    }

    @GetMapping("/{id}")
    fun findFolderTreeById( @PathVariable id: String): FolderTree? {
        return mockService.findById(id)
    }

    @PostMapping("",  produces = ["application/json"], consumes = ["application/json"])
    fun createFolderTree(@RequestBody folderTree: FolderTree): FolderTree {
        return mockService.create(folderTree)
    }

    @PutMapping("/{id}",  produces = ["application/json"], consumes = ["application/json"])
    fun updateFolderTree(@PathVariable id: String, @RequestBody folderTree: FolderTree): FolderTree {
        return mockService.update(id, folderTree)
    }

    @DeleteMapping("/{id}", produces = ["application/json"])
    fun deleteFolderTree(@PathVariable id: String) {
        return mockService.delete(id)
    }

}
