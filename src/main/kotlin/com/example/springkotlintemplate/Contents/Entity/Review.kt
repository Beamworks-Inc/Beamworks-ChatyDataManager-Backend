package com.example.springkotlintemplate.Contents.Entity
import com.example.springkotlintemplate.User.Entity.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,
    @OneToOne(cascade = [CascadeType.DETACH])
    val reviewer : User,
    val reviewDate : LocalDateTime,
    @Column(length = 1000)
    val reviewComment : String
){
    constructor(): this(0, User(), LocalDateTime.now(), "")
}
