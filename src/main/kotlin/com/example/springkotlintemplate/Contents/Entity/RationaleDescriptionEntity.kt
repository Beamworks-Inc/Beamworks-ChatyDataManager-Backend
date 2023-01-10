package com.example.springkotlintemplate.Contents.Entity

import java.net.URL
import javax.persistence.*

@Entity
data class RationaleDescriptionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long,
    val description : String,
    val link : URL
) {
    constructor() : this(0, "", URL("http://www.example.com"))
}
