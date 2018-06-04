package com.example.demo.auth

import com.example.demo.models.UserEntity
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import java.util.HashMap



class CustomTokenEnhancer : TokenEnhancer {
    override fun enhance(accessToken: OAuth2AccessToken?, authentication: OAuth2Authentication?): OAuth2AccessToken {
        val user = authentication?.principal as MyUserDetails
        val additionalInfo = HashMap<String, Any>()

        additionalInfo["id"] = user._userId
        additionalInfo["authorities"] = user.authorities

        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInfo
        println("!@#!@#!@#!#!@#")

        return accessToken
    }
}