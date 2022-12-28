package com.example.springkotlintemplate

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BasicController {

    @GetMapping("/api")
    fun getAPI(): String {
        return "beamworks api"
    }
    @GetMapping("/")
    fun getBase():String{
        return "beamworks backend"
    }
}
