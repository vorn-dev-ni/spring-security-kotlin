package com.example.sss.demo.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Component
class JWTGenerator {

    //    private val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    @Value("\${baeldung.presentation}")
    var secret: String? = ""
    fun generateToken(authentication: Authentication): String {
        val expireDate: Date = Date(Date().time + JTWUtils().expiredDate)

        val token = Jwts.builder().subject(
            authentication.name
        ).issuedAt(Date())
            .expiration(expireDate).signWith(SignatureAlgorithm.HS256, secret)
            .compact()

        return token

    }

    fun getUserDetail(token: String): String {
        try {
            val claims = Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .body;
            return claims.subject;
        } catch (ex: SignatureException) {

            throw SignatureException(ex.message)
        }


    }

    fun isvalidToken(token: String): Boolean {


        print("Checking Token")
        try {
            Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)

            return true
        } catch (ex: AuthenticationCredentialsNotFoundException) {
            print("Error")
            throw AuthenticationCredentialsNotFoundException("JWT is invalid");

        }
    }

}