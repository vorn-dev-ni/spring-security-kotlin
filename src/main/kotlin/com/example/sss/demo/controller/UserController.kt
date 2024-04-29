package com.example.sss.demo.controller

import com.example.sss.demo.model.DTO.LoginRequest
import com.example.sss.demo.model.DTO.TokenResponse
import com.example.sss.demo.model.DTO.UserRequest
import com.example.sss.demo.security.JWTGenerator
import com.example.sss.demo.service.UserService
import io.jsonwebtoken.MalformedJwtException
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.SignatureException

@RestController
@Validated
@RequestMapping("/api/user")
class UserController(var userService: UserService,var authenticationManager: AuthenticationManager,
                     var jwtGenerator: JWTGenerator) {

    @ExceptionHandler(SignatureException::class)
    fun jwtException(ex: SignatureException) : ResponseEntity<Any>{

        return ResponseEntity(ex.message,HttpStatus.UNAUTHORIZED)

    }
    @ExceptionHandler(MalformedJwtException::class)
    fun malwwareForm(ex: MalformedJwtException) : ResponseEntity<Any>{

        print("Unautorize")
        return ResponseEntity(ex.message?:"JWT is invalid",HttpStatus.UNAUTHORIZED)

    }
    @ExceptionHandler(AuthenticationCredentialsNotFoundException::class)
    fun unauthorizeException(ex: AuthenticationCredentialsNotFoundException) : ResponseEntity<Any>{

        print("Unautorize")
        return ResponseEntity(ex.message,HttpStatus.UNAUTHORIZED)

    }
    @PostMapping("/register")
    fun register(@Valid @RequestBody userRequest: UserRequest): ResponseEntity<Any> {
        userService.registerUser(userRequest);

        return ResponseEntity("User has been created",HttpStatus.OK);
    }

   @GetMapping
   fun user(): ResponseEntity<Any>  {
       return ResponseEntity("WWlecome",HttpStatus.OK);

   }
    @PostMapping("/login")
    fun login(@RequestBody loginDto:LoginRequest ): ResponseEntity<Any>
    {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginDto.username,
                loginDto.password
            )
        )
        val token = jwtGenerator.generateToken(authentication)
        SecurityContextHolder.getContext().authentication = authentication
        return ResponseEntity.ok(TokenResponse(token,"Bearer"))
    }

}