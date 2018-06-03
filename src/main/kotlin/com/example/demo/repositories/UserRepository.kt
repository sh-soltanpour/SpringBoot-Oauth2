package com.example.demo.repositories

import com.example.demo.models.UserEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<UserEntity, String> {
    fun findByEmail(email : String) : UserEntity?
}