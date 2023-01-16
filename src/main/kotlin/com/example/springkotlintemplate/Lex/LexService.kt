package com.example.springkotlintemplate.Lex

import com.example.springkotlintemplate.Lex.Dto.LexUpdateDto

interface LexService {
    fun updateQnA(data : List<LexUpdateDto>)
}