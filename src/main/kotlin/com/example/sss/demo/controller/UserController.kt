package com.example.sss.demo.controller

import com.example.sss.demo.model.DTO.LoginRequest
import com.example.sss.demo.model.DTO.TokenRequest
import com.example.sss.demo.model.DTO.TokenResponse
import com.example.sss.demo.model.DTO.UserRequest
import com.example.sss.demo.model.RefreshToken
import com.example.sss.demo.security.JWTGenerator
import com.example.sss.demo.service.RefreshTokenService
import com.example.sss.demo.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping("/api/user")
class UserController(
    var userService: UserService, var authenticationManager: AuthenticationManager,
    var jwtGenerator: JWTGenerator,
    var refreshTokenService: RefreshTokenService
) {


    @PostMapping("/register")
    fun register(@Valid @RequestBody userRequest: UserRequest): ResponseEntity<Any> {
        userService.registerUser(userRequest);

        return ResponseEntity("User has been created", HttpStatus.OK);
    }

    @GetMapping
    fun user(): ResponseEntity<Any> {
        return ResponseEntity("WWlecome", HttpStatus.OK);

    }

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginRequest): ResponseEntity<Any> {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginDto.username,
                loginDto.password
            )
        )
        if (authentication.isAuthenticated) {
//                    refreshTokenService.deleteToken(result)
            val token = jwtGenerator.generateToken(authentication)
            var refreshToken = refreshTokenService.createRefreshToken(authentication.name);
            SecurityContextHolder.getContext().authentication = authentication
            return ResponseEntity.ok(TokenResponse(token, refreshToken.token ?: "", "Bearer"))
        } else {
            throw UsernameNotFoundException("invalid user request..!!");

        }

    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody refreshTokenRequestDTO: TokenRequest): ResponseEntity<Any> {
        val result = refreshTokenService.findByToken(refreshTokenRequestDTO.refreshtoken)
        
        refreshTokenService.deleteToken(result)

        refreshTokenService.verifyExpiration(result)

        val token = jwtGenerator.generateAccessToken(result.userEntities?.username ?: "")
        var refreshToken = refreshTokenService.createRefreshToken(result.userEntities?.username ?: "");
        return ResponseEntity.ok(TokenResponse(token, refreshToken.token ?: "", "Bearer"))


    }


}