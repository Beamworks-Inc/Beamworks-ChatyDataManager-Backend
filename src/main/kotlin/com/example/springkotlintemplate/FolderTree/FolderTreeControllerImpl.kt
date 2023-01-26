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
    override fun createFolderTree(@RequestBody folderTree: FolderTreeRequestDto): FolderTree {
        return folderTreeService.create(folderTree)
    }

    @PostMapping("/appendSiblingsFromRoot")
    override fun appendSiblingFromRoot(@RequestBody siblingNames: List<String>) {
        folderTreeService.addSiblingsFromRoot(siblingNames)
    }
    @PutMapping("/changeName/{id}/{name}")
    override fun changeName(@PathVariable id: Long, @PathVariable name: String): FolderTree {
        return folderTreeService.changeName(name,id)
    }
    @PutMapping("/addChild/{parentId}/{childName}")
    override fun addChild(@PathVariable parentId: Long, @PathVariable childName: String): FolderTree {
        return folderTreeService.addChild(childName,parentId)
    }

    @DeleteMapping("/{id}")
    override fun deleteFolderTree(@PathVariable id: Long): FolderTree? {
        return folderTreeService.deleteById(id)
    }


    @DeleteMapping("")
    override fun deleteAllFolderTree() {
        folderTreeService.deleteAll()
    }

}
