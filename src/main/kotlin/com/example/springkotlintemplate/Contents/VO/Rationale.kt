package com.example.springkotlintemplate.Contents.VO

import java.net.URL
import javax.persistence.*

@Entity
class Rationale(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long,
    @ElementCollection
    val URL : List<URL>,
    @ElementCollection
    val description : List<RationaleDescription>
){
    constructor(): this(0, listOf(), listOf())
}