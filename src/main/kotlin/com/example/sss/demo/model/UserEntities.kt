package com.example.sss.demo.model
import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty


@Table(name = "users")
@Entity
class UserEntities (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,

    @field:Column(name = "username")
    @field:NotEmpty(message = "Username must not be empty")
    var username: String = "",

    @field:Column(name = "password")
    @field:NotEmpty(message = "Password must not be empty")
    var password: String = "",

    @field:Column(name = "roles")
    @field:NotEmpty(message = "Role must not be empty")
    var role: String = "user"
) {
    // No-argument constructor for JPA
    constructor() : this(0, "", "", "")
}
