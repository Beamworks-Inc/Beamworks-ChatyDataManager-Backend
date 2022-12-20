package com.example.springkotlintemplate.Sample.RestDocsTest

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

data class SampleResponse(
    val email: String="이메일",
    val name: String="이름",
    val introduction:String="소개",
    val pictureUrl:String="사진경로",
    val point:String="점수",
    val popularity:String="인기도")
@Controller
class RestDocsSampleController {
    @GetMapping("api/{email}")
    fun sample(@PathVariable email: String): ResponseEntity<SampleResponse> {
        return ResponseEntity.ok(SampleResponse(email = email))
    }
}