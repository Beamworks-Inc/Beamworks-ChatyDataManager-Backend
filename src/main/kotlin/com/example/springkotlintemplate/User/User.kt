package com.example.springkotlintemplate.User

class User (
    val id : Long,
    val name : String
){
    constructor(): this(0, "")
}
