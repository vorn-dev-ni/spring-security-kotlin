package com.example.sss.demo.exception

class JWTException(
    override var message:String = "",


) : RuntimeException(message) {


}