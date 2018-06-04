package com.example.demo.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/private")
class PrivateController {
    @RequestMapping(value=[""], method = [RequestMethod.GET])
    fun test() : ResponseEntity<Unit>{
        return ResponseEntity(HttpStatus.OK)
    }
}