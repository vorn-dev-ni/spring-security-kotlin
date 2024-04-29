package com.example.sss.demo.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.Instant

@Table(name = "refresh_token")
@Entity
data class RefreshToken(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
     var id: Long = 0,

     val token: String? = null,
     val expiryDate: Instant? = null,

    @field:OneToOne
    @field:JoinColumn(name = "user_id", referencedColumnName = "id")
     val userEntities: UserEntities? = null
) {

}
