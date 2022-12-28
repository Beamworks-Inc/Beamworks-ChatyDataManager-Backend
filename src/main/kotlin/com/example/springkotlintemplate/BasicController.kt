package com.example.springkotlintemplate

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

data class Response(
    val description: String
)
@RestController
class BasicController {

    @GetMapping("/api")
    fun getAPI(): Response {
        return Response("beamworks api")
    }
    @GetMapping("/")
    fun getBase(): Response {
        return Response("beamworks")
    }
}
