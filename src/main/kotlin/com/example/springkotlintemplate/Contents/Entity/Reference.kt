package com.example.springkotlintemplate.Contents.Entity

import java.net.URL
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Reference(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long,
    val title : String,
    val description : String,
    val link : URL
){
    constructor(): this(0, "", "", URL("http://www.example.com"))
}
