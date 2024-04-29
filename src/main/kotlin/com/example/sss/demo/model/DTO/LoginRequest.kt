package com.example.sss.demo.model.DTO

import com.example.sss.demo.service.ROLES
import jakarta.validation.constraints.NotEmpty

class LoginRequest
    (
    @field:NotEmpty(message = "username must not be empty")
    var username: String,
    @field:NotEmpty(message = "password must not be empty")
    var password: String,
) {
}