package com.example.springkotlintemplate.User.Entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long,
    val name : String,
    val email : String,
    val role : Role?
){
    constructor(): this(0, "","",null)
}
