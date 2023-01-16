package com.example.springkotlintemplate.Lex

import com.example.springkotlintemplate.Lex.Dto.LexUpdateDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class LexServiceImpl(
    @Value("\${LEX_API_KEY}") val lexApiKey: String,
) : LexService {
    val LEX_URL="https://rzju4ajd8l.execute-api.us-east-1.amazonaws.com/test/uploadIntent"
    override fun updateQnA(data: List<LexUpdateDto>) {
        val restTemplate = RestTemplate()
        val requestBody=ObjectMapper().writeValueAsString(data)
        val headers= HttpHeaders()
        headers.set("x-api-key",lexApiKey)
        headers.set("Content-Type","application/json")
        val request=HttpEntity(requestBody,headers)
        val response=restTemplate.postForEntity(LEX_URL,request,String::class.java)
        if(response.statusCode!= HttpStatus.OK){
            print(response.body)
            throw Exception("Lex update failed")
        }
    }
}