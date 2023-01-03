package com.example.springkotlintemplate.Contents.VO

import java.net.URL
import javax.persistence.Embeddable
import javax.persistence.Id

@Embeddable
class RationaleDescription(
    @Id
    val id : Long,
    val description : String,
    val link : URL
) {
    constructor() : this(0, "", URL("http://www.example.com"))
}
