package com.example.springkotlintemplate.Contents.Entity

import javax.persistence.*

@Entity
data class Rationale(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long,
    @ElementCollection
    @Column(length = 1000)
    val URL : MutableList<String>,
    @OneToMany(cascade = [CascadeType.ALL])
    val description : MutableList<RationaleDescriptionEntity>
){
    constructor(): this(0, mutableListOf(), mutableListOf())
}