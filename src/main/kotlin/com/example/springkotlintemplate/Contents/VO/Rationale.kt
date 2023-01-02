package com.example.springkotlintemplate.Contents.VO

import java.net.URL

class Rationale(
    val id : Long,
    val URL : List<URL>,
    val description : List<RationaleDescription>
){
    constructor(): this(0, listOf(), listOf())
}