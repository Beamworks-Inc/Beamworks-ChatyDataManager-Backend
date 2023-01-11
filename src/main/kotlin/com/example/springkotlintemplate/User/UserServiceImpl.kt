package com.example.springkotlintemplate.User

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
        val userEmail: String=getUserEmail()
        return userRepository.findByEmail(userEmail) ?: throw UserNotFoundException()
    }

    override fun getUserEmail(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        if(!(authentication is AnonymousAuthenticationToken)){
            return (authentication.principal as DefaultOAuth2User).attributes["email"] as String
        }
        else {
            throw NotAuthenticateException()
        }
    }
}