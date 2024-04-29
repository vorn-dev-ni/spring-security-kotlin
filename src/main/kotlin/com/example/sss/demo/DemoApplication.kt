package com.example.sss.demo

import com.example.sss.demo.security.JTWEntrypoint
import com.example.sss.demo.security.JTWFilterChain
import com.example.sss.demo.service.CustomUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authorization.AuthenticatedAuthorizationManager
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@SpringBootApplication()

class DemoApplication {
    @Bean
    fun PasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
    @Bean
    open fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration):
            AuthenticationManager {
        return authenticationConfiguration.authenticationManager;
    }

//    @Bean
//    fun jwtAuthenticationFilter(): JTWFilterChain? {
//        return JTWFilterChain()
//    }

}

fun main(args: Array<String>) {

    runApplication<DemoApplication>(*args)
}


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: CustomUserService,
    private val authEntryPoint:JTWEntrypoint,
    private val jtwFilterChain: JTWFilterChain

    ) {


    //
//    @Bean
//    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
//        return authenticationConfiguration.authenticationManager
//    }

    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        print("User custom run")
        http.invoke {
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS

            }
            authorizeHttpRequests {

                authorize("/css/**", permitAll)
                authorize("/api/user**", permitAll)

                authorize("/api/dummy**", hasAuthority("admin"))

//				authorize("/api/**",hasAnyRole("ADMIN","USER"))

//                authorize(HttpMethod.POST,"api/dummy/**",hasRole("ADMIN"),)
                authorize(anyRequest, permitAll)

            }

            cors {
                disable()
            }
            csrf {
                disable()
            }
            formLogin { }
            httpBasic {

            }
            logout {
                logoutUrl = "/ok/logout"
            }

        }
        http.addFilterBefore(jtwFilterChain, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}




//    @Bean
//    open fun createUser() : UserDetailsService {
//
//        val user = User.builder()
//            .username("user")
//            .password(passwordEncoder().encode("vorni"))
//            .roles("USER")
//            .build()
//
//
//        val user2 = User.builder()
//            .username("admin")
//            .password(passwordEncoder().encode("vorni"))
//            .roles("ADMIN")
//            .build()
//        print("user1 is ${user.password}")
//        print("user2 is ${user.password}")
//        return InMemoryUserDetailsManager(user,user2)
//
//
//    }



