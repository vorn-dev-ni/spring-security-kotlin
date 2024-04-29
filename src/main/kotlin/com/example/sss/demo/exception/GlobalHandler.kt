//package com.example.sss.demo.exception
//
//import org.springframework.http.ResponseEntity
//import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
//import org.springframework.web.bind.annotation.ControllerAdvice
//import org.springframework.web.bind.annotation.ExceptionHandler
//import java.security.SignatureException
//
//@ControllerAdvice
//class GlobalHandler {
//
//    @ExceptionHandler(SignatureException::class)
//    fun jwtException(ex: SignatureException) : ResponseEntity<Any>{
//
//            return ResponseEntity.badRequest().body(ex.message)
//
//    }
//
//    @ExceptionHandler(AuthenticationCredentialsNotFoundException::class)
//    fun unauthorizeException(ex: AuthenticationCredentialsNotFoundException) : ResponseEntity<Any>{
//
//        return ResponseEntity.badRequest().body(ex.message)
//
//    }
//}