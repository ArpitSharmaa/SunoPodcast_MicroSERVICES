package com.example

import com.example.JWTtokken.TokenConfig
import com.example.JWTtokken.TokengnerateService
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import java.util.concurrent.ConcurrentHashMap

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val tokenService= TokengnerateService()
    val tokenconfig = TokenConfig(
        issuer = "http://0.0.0.0:8080",
        audience = "users",
        expiresIn = 365L*1000L*60L*60L*24L,
        secret = "sunopodcast"
    )
    val livepodcasts = ConcurrentHashMap<String,String>()
    configureSockets(livepodcasts)
    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenconfig)
    configureRouting(livepodcasts,tokenService,tokenconfig)
}
