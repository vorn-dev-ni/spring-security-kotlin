package com.example.sss.demo.security

import com.example.sss.demo.service.CustomUserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@EnableAutoConfiguration
class JTWFilterChain(
    private val jwtGenerator: JWTGenerator, private var userDetails: CustomUserService
) : OncePerRequestFilter() {


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {


        val token = getJWTheader(request)

        if (getJWTheader(request) != null && jwtGenerator.isvalidToken(token ?: "")) {
            val username = jwtGenerator.getUserDetail(token ?: "");
            print("Username ${username}")
            val userdetail = userDetails.loadUserByUsername(username)

            val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                userdetail,
                userdetail.username,
                userdetail.authorities
            )
            usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken


        }


        return filterChain.doFilter(request, response)

    }

    private fun getJWTheader(request: HttpServletRequest): String? {

        val header = request?.getHeader("Authorization") ?: ""
        if (header.startsWith("Bearer") && header.isNotEmpty()) {
            print("Token ${header.split(" ")[1]}")
            return header.split(" ")[1]
        }
        return null

    }
}