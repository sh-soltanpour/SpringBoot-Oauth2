package com.example.demo.controllers

import com.example.demo.models.endpoints.SignupRequest
import com.example.demo.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {

    @Autowired
    lateinit var userService : UserService

    @RequestMapping(value=[""], method = [RequestMethod.GET])
    fun testCont() : ResponseEntity<Unit>{
        return ResponseEntity(HttpStatus.OK)
    }

    @RequestMapping(value=["/signup"], method = [RequestMethod.POST])
    fun signup(@RequestBody request : SignupRequest) : ResponseEntity<Unit>{
        userService.signup(request.email !!, request.password!!)
        return ResponseEntity(HttpStatus.OK)
    }
}