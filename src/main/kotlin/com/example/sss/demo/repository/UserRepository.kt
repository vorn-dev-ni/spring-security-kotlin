package com.example.sss.demo.repository

import com.example.sss.demo.model.UserEntities
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository  : JpaRepository <UserEntities,Long> {


    fun findByUsername(username: String): Optional<UserEntities>

}