package com.example.sss.demo.service

import com.example.sss.demo.exception.JWTException
import com.example.sss.demo.model.RefreshToken
import com.example.sss.demo.model.UserEntities
import com.example.sss.demo.repository.TokenRepository
import com.example.sss.demo.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Instant
import java.util.*


@Service
class RefreshTokenService(
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository
) {


    fun createRefreshToken(username: String):RefreshToken {
        var userdetail = userRepository.findByUsername(username = username)
            .orElseThrow { throw ResponseStatusException(HttpStatus.BAD_REQUEST) }


        val refreshToken = RefreshToken(

            expiryDate = Instant.now().plusMillis(600000),
            token = (UUID.randomUUID().toString()),
            userEntities = userdetail
        )
         tokenRepository.save(refreshToken)
        return refreshToken
    }

    fun findByToken(refreshtoken: String?): RefreshToken {
       return   tokenRepository.findByToken(refreshtoken).orElseThrow {
           throw  JWTException("invalid token")
        }
    }

    fun verifyExpiration(token: RefreshToken): String {
        if ((token.expiryDate?.compareTo(Instant.now()) ?: 0) < 0) {
            tokenRepository.delete(token)
            throw JWTException(token.token + " Refresh token is expired. Please make a new login..!")
        }
        return token.token?:""
    }

    fun deleteToken(token:RefreshToken) {
        tokenRepository.delete(token)
    }

}