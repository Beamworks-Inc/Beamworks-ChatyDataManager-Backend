package com.example.springkotlintemplate.User

import com.example.springkotlintemplate.User.Entity.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserControllerImpl(
    private val userService: UserService
): UserController {
    @GetMapping("")
    override fun getUserInfo(): User {
        return userService.getCurrentUserInfo()
    }
}