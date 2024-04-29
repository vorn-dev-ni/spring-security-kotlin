package com.example.sss.demo.service

import com.example.sss.demo.model.DTO.UserRequest
import com.example.sss.demo.model.UserEntities
import com.example.sss.demo.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.lang.RuntimeException


@Service
class UserServiceImp(var userRepository: UserRepository, var passwordEncoder: PasswordEncoder) : UserService {
    override fun findByUsername(username: String): UserEntities? {
       return userRepository.findByUsername(username.trim()).orElseThrow { throw RuntimeException("User name not found") }

    }

    override fun registerUser(userrequest: UserRequest): UserEntities? {
        //validator later
        var user = UserEntities(username = userrequest.username, password =passwordEncoder.encode( userrequest.password), role = userrequest.roles.toString())
      return  userRepository.save(user);
    }
}