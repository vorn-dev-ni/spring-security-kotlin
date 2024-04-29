package com.example.sss.demo.service

import com.example.sss.demo.model.DTO.UserRequest
import com.example.sss.demo.model.UserEntities
import org.springframework.stereotype.Service


interface UserService {

    fun findByUsername(username: String): UserEntities?
    fun registerUser(userrequest:UserRequest): UserEntities?

}