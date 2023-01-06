package com.example.springkotlintemplate.Contents.Entity
import com.example.springkotlintemplate.User.User
import java.time.LocalDateTime
import java.util.Date
import javax.persistence.*

@Entity
data class Review(
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    val id: Int,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user.id")
    val reviewer : User,
    val reviewDate : LocalDateTime,
    val reviewComment : String
){
    constructor(): this(0, User(), LocalDateTime.now(), "")
}
