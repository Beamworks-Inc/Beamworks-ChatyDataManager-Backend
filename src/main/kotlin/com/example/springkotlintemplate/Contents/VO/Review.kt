package com.example.springkotlintemplate.Contents.VO
import com.example.springkotlintemplate.User.User
import java.util.Date

data class Review(
    val id: Int,
    val reviewer : User,
    val reviewDate : Date,
    val reviewComment : String
){
    constructor(): this(0, User(), Date(), "")
}
