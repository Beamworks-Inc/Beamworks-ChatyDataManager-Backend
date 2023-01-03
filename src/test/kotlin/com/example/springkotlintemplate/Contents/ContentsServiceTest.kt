package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.Exception.ContentsNotFoundException
import com.example.springkotlintemplate.Contents.VO.Contents
import com.example.springkotlintemplate.FolderTree.Exception.FolderTreeNotFoundException
import com.example.springkotlintemplate.FolderTree.FolderTreeService
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class ContentsServiceTest() : DescribeSpec({
    val mockContentsRepository = mockk<ContentsRepository>()
    val mockFolderTreeService = mockk<FolderTreeService>()
    val service = ContentsServiceImpl(mockContentsRepository,mockFolderTreeService)
    val mockContents= Contents()
    describe("ContentsServiceTest") {
        context("컨텐츠 데이터 가져오기") {
            every { mockContentsRepository.findAllByFolderName(any()) } returns listOf(mockContents)
            it("폴더 이름을 입력받아서 폴더 내부의 파일 목록을 반환한다.") {
                service.findAllByFolderName("folderName") shouldBe listOf(mockContents)
            }
        }
        context("컨텐츠 데이터 생성하기"){
            context("컨텐츠 데이터에 명시된 폴더 이름이 존재하지 않는 경우"){
                every { mockFolderTreeService.findById(any()) } returns null
                it("FolderTreeNotFoundException 을 던진다.") {
                    shouldThrowExactly<FolderTreeNotFoundException> {
                        service.create(mockContents)
                    }
                }
            }
            context("컨텐츠 데이터에 명시된 폴더 이름이 존재하는 경우"){
                every { mockFolderTreeService.findById(any()) } returns mockk()
                every { mockContentsRepository.save(any()) } returns mockContents
                it("컨텐츠 데이터를 생성한다.") {
                    service.create(mockContents) shouldBe mockContents
                }
            }
        }
        context("컨텐츠 데이터 업데이트 하기"){
            context("업데이트 할 컨텐츠 데이터가 존재하지 않는 경우"){
                every { mockContentsRepository.findById(any()) } returns Optional.empty()
                it("ContentsNotFoundException 을 던진다.") {
                    shouldThrowExactly<ContentsNotFoundException> {
                        service.update(1,mockContents)
                    }
                }
            }
            context("업데이트 할 컨텐츠 데이터가 존재하는 경우"){
                every { mockContentsRepository.findById(any()) } returns Optional.of(mockContents)
                every { mockContentsRepository.save(any()) } returns mockContents
                it("컨텐츠 데이터를 업데이트 한다.") {
                    service.update(1,mockContents) shouldBe mockContents
                }
            }
        }
        context("컨텐츠 데이터 삭제하기"){
            context("삭제 할 컨텐츠 데이터가 존재하지 않는 경우"){
                every { mockContentsRepository.findById(any()) } returns Optional.empty()
                it("ContentsNotFoundException 을 던진다.") {
                    shouldThrowExactly<ContentsNotFoundException> {
                        service.delete(1)
                    }
                }
            }
            context("삭제 할 컨텐츠 데이터가 존재하는 경우"){
                every { mockContentsRepository.findById(any()) } returns Optional.of(mockContents)
                every { mockContentsRepository.deleteById(any()) } returns Unit
                it("컨텐츠 데이터를 삭제한다.") {
                    service.delete(1) shouldBe Unit
                }
            }
        }

    }
})
