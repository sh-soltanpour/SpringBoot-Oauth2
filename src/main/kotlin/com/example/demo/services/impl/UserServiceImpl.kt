package com.example.demo.services.impl

import com.example.demo.auth.MyUserDetails
import com.example.demo.models.UserEntity
import com.example.demo.repositories.UserRepository
import com.example.demo.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class UserDetailsServiceImpl : UserDetailsService, UserService {
    @Autowired
    private lateinit var userRepository : UserRepository

    override fun loadUserByUsername(username: String?): UserDetails? {
        if (username == null)
            return null
        val user = userRepository.findByEmail(username)?: throw ClassNotFoundException("User $username not found")
        val userDetails = MyUserDetails()
        userDetails._username = user.email!!
        userDetails._password = user.password!!
        userDetails.grants = user.getAuthorities()
        userDetails._userId = user.id!!
        return userDetails
    }

    override fun signup(email: String, password: String) {
        val user = UserEntity()
        user.email = email
        user.password = password
        user.roles = "client"
        userRepository.save(user)

    }
}