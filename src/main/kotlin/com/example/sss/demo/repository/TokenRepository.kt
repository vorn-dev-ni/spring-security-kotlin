package com.example.sss.demo.repository

import com.example.sss.demo.model.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TokenRepository: JpaRepository<RefreshToken,Long> {

    fun findByToken(token: String?): Optional<RefreshToken>

}