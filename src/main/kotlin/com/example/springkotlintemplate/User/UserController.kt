package com.example.springkotlintemplate.User

import com.example.springkotlintemplate.User.Entity.User

interface UserController {
    fun getUserInfo(): User;
}