package com.example.sss.demo.service
import com.example.sss.demo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.lang.RuntimeException

enum  class  ROLES  {
    admin,sale,user
}


@Service
  class CustomUserService : UserDetailsService {



    lateinit var userRepository: UserRepository
    lateinit var passwordEncoder: PasswordEncoder

    init {
        print("User custom cerated")
    }

    @Autowired
    constructor(userRepository: UserRepository, passwordEncoder: PasswordEncoder) {
        this.userRepository = userRepository
        this.passwordEncoder = passwordEncoder
    }


    override fun loadUserByUsername(username: String?): UserDetails {
//        TODO("Not yet implemented")


        val user=   userRepository.findByUsername(username?:"").orElseThrow { throw  RuntimeException("sads") };
        println("Attempting to load user with username: ${user.role}")
        return User(
           user?.username,
            user?.password,
            getAuthorities(listOf(user.role))

        )
    }
        fun getAuthorities( role:List<String>): Collection<GrantedAuthority> {
        return role.map { it->SimpleGrantedAuthority(it.toString()) };

    }
//    fun getAuthorities( role:List<ROLES>): Collection<GrantedAuthority> {
//        return role.map { it->SimpleGrantedAuthority(it.toString()) };
//
//    }


}