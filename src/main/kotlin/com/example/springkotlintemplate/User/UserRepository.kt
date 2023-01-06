package com.example.springkotlintemplate.User

import com.example.springkotlintemplate.User.Entity.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User,Long> {
    fun findByEmail(email: String): User?
}
