package com.example.springkotlintemplate.Contents.VO

import java.net.URL

class Reference(
    val id : Long,
    val title : String,
    val description : String,
    val link : URL
){
    constructor(): this(0, "", "", URL("http://www.example.com"))
}
