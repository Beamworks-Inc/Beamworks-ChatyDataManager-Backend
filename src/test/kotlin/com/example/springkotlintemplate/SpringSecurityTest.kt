package com.example.springkotlintemplate

import io.kotest.core.spec.style.DescribeSpec
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
class SpringSecurityTest:DescribeSpec({
    describe("Spring Security Test"){
        it("로그인 전에 공개 URL 접근 시 200 응답 코드 반환"){
            // TODO
        }
        it("로그인 전에 API 접근시 401 에러 발생"){
            //TODO
        }
        it("로그인 후에 API 접근시 200 응답 코드 반환"){
            //TODO
        }
        describe("OAuth2 로그인"){

        }
    }
})
