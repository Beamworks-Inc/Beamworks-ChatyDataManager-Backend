package com.example.springkotlintemplate.Contents.Entity

import java.net.URL
import javax.persistence.*

@Entity
class Reference(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long,
    val title : String,
    @Column(length = 1000)
    val description : String,
    @Column(length = 1000)
    val link : URL
){
    constructor(): this(0, "", "", URL("http://www.example.com"))
}
