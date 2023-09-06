package com.example.plugins

import com.example.DataClasses.response
import com.example.JWTtokken.TokenConfig
import com.example.JWTtokken.TokengnerateService
import com.example.plugins.registeration.auth
import com.example.plugins.registeration.getsecret
import com.example.plugins.registeration.login
import com.example.plugins.registeration.registeration
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import java.util.concurrent.ConcurrentHashMap

fun Application.configureRouting(livepodcast:ConcurrentHashMap<String,String>
                                 , tokengnerateService: TokengnerateService
                                 , tokenConfig: TokenConfig) {
    routing {

        get("/podcasts") {
            val respond= ArrayList(livepodcast.keys)

            call.respond(HttpStatusCode.OK,response(respond))
        }
        registeration()
        login(tokengnerateService,tokenConfig)
        auth()
        getsecret()
        staticResources("/Static","Static")
    }
}
