package com.example.springkotlintemplate.Config.Security


import com.example.springkotlintemplate.User.Entity.User
import com.example.springkotlintemplate.User.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service


@Service
class CustomOauth2UserService(
    private val userRepository: UserRepository,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val delegate: OAuth2UserService<OAuth2UserRequest, OAuth2User> = DefaultOAuth2UserService()
        val oAuth2User: OAuth2User = delegate.loadUser(userRequest)
        val name: String = oAuth2User.attributes["name"] as String
        val email: String = oAuth2User.attributes["email"] as String
        val userNameAttributeName = userRequest!!.clientRegistration.providerDetails
            .userInfoEndpoint.userNameAttributeName

        if(isUserNotExist(email)){
            userRepository.save(User(0, name, email,null))
        }

        return DefaultOAuth2User(
            oAuth2User.authorities,
            oAuth2User.attributes,
            userNameAttributeName
        )
    }

    private fun isUserNotExist(email : String): Boolean {
        return userRepository.findByEmail(email) == null
    }


}