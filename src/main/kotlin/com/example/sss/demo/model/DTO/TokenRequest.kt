package com.example.sss.demo.model.DTO

import com.fasterxml.jackson.annotation.JsonProperty


data class TokenRequest (

    @field:JsonProperty("refresh")
    val refreshtoken:String
){
}