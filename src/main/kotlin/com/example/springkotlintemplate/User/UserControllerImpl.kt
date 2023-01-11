package com.example.springkotlintemplate.User

import com.example.springkotlintemplate.User.Entity.Role
import com.example.springkotlintemplate.User.Entity.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserControllerImpl(
    private val userService: UserService
): UserController {
    @GetMapping("")
    override fun getUserInfo(): User {
        return userService.getCurrentUserInfo()
    }
    @PostMapping("/role/{role}")
    override fun updateUserRole(@PathVariable role: Role) {
        userService.updateUserRole(role)
    }
}