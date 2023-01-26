package com.example.springkotlintemplate.User

import com.example.springkotlintemplate.User.Entity.Role
import com.example.springkotlintemplate.User.Entity.User
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    override fun getCurrentUserInfo(): User {
        val userEmail: String=getUserEmailFromSecurityContext()
        return userRepository.findByEmail(userEmail) ?: throw UserNotFoundException()
    }

    private fun getUserEmailFromSecurityContext(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        if(!(authentication is AnonymousAuthenticationToken)){
            return (authentication.principal as DefaultOAuth2User).attributes["email"] as String
        }
        else {
            throw NotAuthenticateException()
        }
    }

    override fun updateUserRole(role: Role) {
        val user: User = getCurrentUserInfo()
        userRepository.save(user.copy(role = role))
    }
}