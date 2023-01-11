package com.example.springkotlintemplate.User

import com.example.springkotlintemplate.User.Entity.Role
import com.example.springkotlintemplate.User.Entity.User


interface UserService {
    fun getCurrentUserInfo(): User
    fun updateUserRole(role: Role)
}
