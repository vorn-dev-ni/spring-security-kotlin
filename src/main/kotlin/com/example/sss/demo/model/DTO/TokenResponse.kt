package com.example.sss.demo.model.DTO

import com.example.sss.demo.service.ROLES
import jakarta.validation.constraints.NotEmpty

class TokenResponse(

    var accessToken:String,
    var tokenType:String,

) {
}