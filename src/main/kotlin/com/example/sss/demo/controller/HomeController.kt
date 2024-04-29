package com.example.sss.demo.controller

import jakarta.annotation.security.RolesAllowed
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/dummy")
class HomeController {
    @Value("\${baeldung.presentation}")
    final lateinit var key:String


    @GetMapping
    fun getDummy() : ResponseEntity<Any> {
        val dummydata = listOf<Any>(
            mapOf(

                "id" to "1",
                "product" to "iphone",
            ),
            mapOf(

                "id" to "2",
                "product" to "Samsung",
            )
        )

        return ResponseEntity(key,HttpStatus.OK)
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun postDummy(@RequestBody dummy:Any) : ResponseEntity<Any> {


        return ResponseEntity(dummy,HttpStatus.OK)
    }
}