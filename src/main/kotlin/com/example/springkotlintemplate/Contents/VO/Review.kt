package com.example.springkotlintemplate.Contents.VO
import com.example.springkotlintemplate.User.User
import java.util.Date
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class Review(
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    val id: Int,
    @OneToOne val reviewer : User,
    val reviewDate : Date,
    val reviewComment : String
){
    constructor(): this(0, User(), Date(), "")
}
