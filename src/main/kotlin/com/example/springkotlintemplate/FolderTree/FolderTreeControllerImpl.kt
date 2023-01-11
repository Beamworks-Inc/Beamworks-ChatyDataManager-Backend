package com.example.springkotlintemplate.FolderTree

import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeRequestDto
import com.example.springkotlintemplate.FolderTree.Entity.FolderTree
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/folder")
class FolderTreeControllerImpl(private val folderTreeService: FolderTreeService): FolderTreeController {
    @GetMapping("")
    override fun findAllRootFolder(): List<FolderTree> = folderTreeService.findAllRootFolder()

    @GetMapping("/{id}")
    override fun findFolderTreeById( @PathVariable id: Long): FolderTree? = folderTreeService.findById(id)

    @PostMapping("")
    override fun createFolderTree(@RequestBody folderTree: FolderTreeRequestDto) {
        folderTreeService.create(folderTree)
    }

    @PutMapping("/{id}")
    override fun updateFolderTree(@PathVariable id: Long,@RequestBody folderTree: FolderTreeRequestDto): FolderTree {
        return folderTreeService.update(id,folderTree)
    }

    @DeleteMapping("/{id}")
    override fun deleteFolderTree(@PathVariable id: Long) {
        folderTreeService.deleteById(id)
    }

    @DeleteMapping("")
    override fun deleteAllFolderTree() {
        folderTreeService.deleteAll()
    }

}
