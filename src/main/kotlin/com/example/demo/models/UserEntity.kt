package com.example.demo.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.io.Serializable

@Document(collection="users")
class UserEntity : Serializable {
    @Id
    var id : String? = null

    @Indexed(unique = true)
    var email : String? = null
    var password : String? = null
    var roles : String? = null

    @Transient
    fun getAuthorities() : List<GrantedAuthority>{
        val grants = ArrayList<GrantedAuthority>()
        for (role in roles!!.split(",")){
            grants.add(SimpleGrantedAuthority(role))
        }
        return grants
    }

}