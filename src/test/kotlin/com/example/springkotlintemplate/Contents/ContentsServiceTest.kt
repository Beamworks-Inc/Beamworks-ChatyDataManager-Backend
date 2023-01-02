package com.example.springkotlintemplate.Contents

import com.example.springkotlintemplate.Contents.Exception.ContentsNotFoundException
import com.example.springkotlintemplate.Contents.VO.Contents
import com.example.springkotlintemplate.FolderTree.FolderTreeService
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.mockito.ArgumentMatchers.any

class ContentsServiceTest() : DescribeSpec({
    val mockContentsRepository = mockk<ContentsRepository>()
    val mockFolderTreeService = mockk<FolderTreeService>()
    val service = ContentsServiceImpl(mockContentsRepository,mockFolderTreeService)
    val mockContents= Contents()
    describe("ContentsServiceTest") {
        context("컨텐츠 데이터 가져오기") {
            it("폴더 이름을 입력받아서 폴더 내부의 파일 목록을 반환한다.") {
                every { mockContentsRepository.findAllByFolderName(any()) } returns listOf(mockContents)
                service.findAllByFolderName("folderName") shouldBe listOf(mockContents)
            }
        }
        context("컨텐츠 데이터 생성하기"){
            it("컨텐츠 데이터에 명시된 폴더 이름이 존재하지 않는 경우 FolderNotFoundException 을 반환한다."){
                every{mockFolderTreeService.findById(mockContents.folder.name)} throws FolderNotFoundException()
                service.create(mockContents) shouldBe FolderNotFoundException()
            }
            it("폴더 이름이 존재하는 경우 컨텐츠 데이터를 생성한다."){
                every { mockContentsRepository.create(mockContents) } returns mockContents
                service.create(mockContents) shouldBe mockContents
            }
        }
        context("컨텐츠 데이터 업데이트 하기"){
            it("컨텐츠 데이터에 명시된 폴더 이름이 존재하지 않는 경우 FolderNotFoundException 을 반환한다."){
                every{mockFolderTreeService.findById(any())} throws FolderNotFoundException()
                service.update(any(),mockContents) shouldBe FolderNotFoundException()
            }
            it("컨텐츠 데이터에 명시된 컨텐츠 이름이 존재하지 않는 경우 ContentsNotFoundException 을 반환한다."){
                every { mockContentsRepository.findById(any()) } throws ContentsNotFoundException()
                shouldThrowExactly<ContentsNotFoundException> {
                    service.update(any(),mockContents)
                }
             }
            it("위 두가지 예외가 아닌 경우 컨텐츠 데이터를 업데이트한다."){
                every { mockContentsRepository.findById(any()) } returns mockContents
                every { mockContentsRepository.findById(any()) } returns mockContents
                service.update(any(),mockContents) shouldBe mockContents
            }
        }
        context("컨텐츠 데이터 삭제하기"){
            it("컨텐츠 데이터에 명시된 컨텐츠 이름이 존재하지 않는 경우 ContentsNotFoundException 을 반환한다."){
                every { mockContentsRepository.findById(any()) } throws ContentsNotFoundException()
                shouldThrowExactly<ContentsNotFoundException> {
                    service.delete(any())
                }
            }
            it("컨텐츠가 존재하는 경우 컨텐츠 데이터를 삭제하고 결과를 반환한다."){
                every { mockContentsRepository.findById(any()) } returns mockContents
                service.delete(any()) shouldBe mockContents
            }
        }

    }
})
