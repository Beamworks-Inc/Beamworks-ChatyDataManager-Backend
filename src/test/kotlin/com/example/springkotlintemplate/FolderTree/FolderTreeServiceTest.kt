//package com.example.springkotlintemplate.FolderTree
//
//import com.example.springkotlintemplate.FolderTree.Dto.FolderTreeRequestDto
//import com.example.springkotlintemplate.FolderTree.Exception.FolderTreeNotFoundException
//import com.example.springkotlintemplate.FolderTree.Entity.FolderTree
//import io.kotest.assertions.throwables.shouldThrowExactly
//import io.kotest.core.spec.style.DescribeSpec
//import io.kotest.matchers.shouldBe
//import io.mockk.every
//import io.mockk.mockk
//import org.mockito.ArgumentMatchers.any
//
//class FolderTreeServiceTest: DescribeSpec({
//    val mockRepository = mockk<FolderTreeRepository>()
//    val service = FolderTreeServiceImpl(mockRepository)
//    val newFolderTreeData= FolderTreeRequestDto("root", mutableListOf(
//        FolderTreeRequestDto("child1", mutableListOf(
//            FolderTreeRequestDto("grandchild1", mutableListOf()),
//            FolderTreeRequestDto("grandchild2", mutableListOf())
//        )),
//        FolderTreeRequestDto("child2", mutableListOf())
//    ))
//    val mockFolderTree=FolderTree()
//    describe("폴더 트리 서비스") {
//        context("새로운 폴더 트리 데이터 생성") {
////            context("이미 같은 이름의 폴더 트리 데이터가 존재한다면"){
////                every { mockRepository.findByName(newFolderTreeData.name) } returns newFolderTreeData
////                    it("에러를 발생시킨다.") {
////                        shouldThrowExactly<FolderTreeAlreadyExistException> {
////                            service.create(any())
////                        }
////                    }
////                }
////            }
//            context("이미 같은 이름의 폴더 트리 데이터가 존재하지 않는다면") {
//                every { mockRepository.findByName(newFolderTreeData.name) } returns null
//                every { mockRepository.save(any()) } returns mockFolderTree
//                it("새로운 폴더 트리 데이터를 생성하고 반환해야한다.") {
//                    service.create(any()).shouldBe(mockFolderTree)
//                }
//            }
//        }
//        context("폴더 트리 데이터 수정") {
////            val previousFolderTree = FolderTree("root", listOf(
////                FolderTree("child2", listOf())
////            ))
//            context("변경 대상인 폴더 트리 데이터의 이름이 존재하지 않는다면") {
////                every { mockRepository.findByName(previousFolderTree.name) } returns null
////                it("대상이 존재하지 않는다는 에러를 발생시킨다."){
////                    shouldThrowExactly<FolderTreeNotFoundException> {
////                        service.update(previousFolderTree.name, newFolderTreeData)
////                    }
////                }
//            }
////            context("변경 대상인 폴더 트리 데이터의 이름이 존재한다면"){
////                every { mockRepository.findByName(previousFolderTree.name) } returns newFolderTreeData
////                every { mockRepository.save(newFolderTreeData) } returns newFolderTreeData
////                it("새로운 폴더 트리 데이터를 생성하고 반환해야한다."){
////                    service.update(previousFolderTree.name, newFolderTreeData).shouldBe(newFolderTreeData)
////                }
////            }
//        }
//        context("폴더 트리 데이터 삭제") {
////            context("폴더 트리 데이터가 존재할 때"){
////                every { mockRepository.findByName(newFolderTreeData.name) } returns newFolderTreeData
////                every { mockRepository.deleteByName(newFolderTreeData.name) } returns Unit
////                it("폴더 트리 데이터를 삭제해야한다."){
////                    service.delete(newFolderTreeData.name).shouldBe(Unit)
////                }
////            }
//            context("폴더 트리 데이터가 존재하지 않을 때") {
//                every { mockRepository.findByName(newFolderTreeData.name) } returns null
//                it("예외를 던져야한다.") {
//                    shouldThrowExactly<FolderTreeNotFoundException> {
//                        service.deleteById(any())
//                    }
//                }
//            }
//        }
//        context("폴더 트리 데이터 조회") {
//            context("모든 폴더 트리 데이터 조회") {
////                every { mockRepository.findAll() } returns listOf(newFolderTreeData)
////                context("폴더 트리 데이터가 존재한다면"){
////                    it("폴더 트리 데이터를 반환해야한다."){
////                        service.findAll().shouldBe(listOf(newFolderTreeData))
////                    }
////                }
//                context("폴더 트리 데이터가 존재하지 않는다면") {
//                    every { mockRepository.findAll() } returns listOf()
//                    it("빈 리스트를 반환해야한다.") {
//                        service.findAllRootFolder().shouldBe(listOf())
//                    }
//                }
//            }
//            context("특정 폴더 트리 데이터 조회") {
////                context("폴더 트리 데이터가 존재한다면"){
////                    every { mockRepository.findByName(newFolderTreeData.name) } returns newFolderTreeData
////                    it("폴더 트리 데이터를 반환해야한다."){
////                        service.findById(newFolderTreeData.name).shouldBe(newFolderTreeData)
////                    }
////                }
//                context("폴더 트리 데이터가 존재하지 않는다면") {
//                    every { mockRepository.findByName(newFolderTreeData.name) } returns null
//                    it("예외를 던져야한다.") {
//                        shouldThrowExactly<FolderTreeNotFoundException> {
//                            service.findById(any())
//                        }
//
//                    }
//                }
//            }
//        }
//    }
//})