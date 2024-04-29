//import com.example.sss.demo.security.JTWFilterChain
//import com.example.sss.demo.service.CustomUserService
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.annotation.web.invoke
//import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
//
//@Configuration
//@EnableWebSecurity
//class SecurityConfig(
//    private val userDetailsService: CustomUserService,
//
//    ) {
//
//    @Autowired
//    lateinit var jtwFilterChain: JTWFilterChain
//
//
//    //
////    @Bean
////    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
////        return authenticationConfiguration.authenticationManager
////    }
//
//    @Bean
//    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        print("User custom cerated")
//        http.invoke {
//
//            authorizeHttpRequests {
//
//                authorize("/css/**", permitAll)
//                authorize("/api/user**", permitAll)
//
//                authorize("/api/dummy**", hasAnyAuthority("admin", "ROLE_admin"))
//
////				authorize("/api/**",hasAnyRole("ADMIN","USER"))
//
////                authorize(HttpMethod.POST,"api/dummy/**",hasRole("ADMIN"),)
//                authorize(anyRequest, authenticated)
//
//            }
//            sessionManagement {
//                sessionCreationPolicy = SessionCreationPolicy.STATELESS
//
//            }
//            cors {
//                disable()
//            }
//            csrf {
//                disable()
//            }
//            formLogin { }
//            logout {
//                logoutUrl = "/ok/logout"
//            }
//            httpBasic {
//            }
//        }
//        http.addFilterBefore(jtwFilterChain, UsernamePasswordAuthenticationFilter::class.java)
//        return http.build()
//    }
//}
