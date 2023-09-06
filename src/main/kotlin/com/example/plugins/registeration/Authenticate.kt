package com.example.plugins.registeration


import com.example.DataClasses.replytoclient
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.auth(){
    authenticate {
        get("authenticate") {
            call.respond(HttpStatusCode.OK, replytoclient(""))
        }
    }

}
fun Route.getsecret(){
    authenticate {
        get ("/secret"){
            val principal = call.principal<JWTPrincipal>()
            val userid = principal?.getClaim("Useremail",String::class)
            call.respond(HttpStatusCode.OK,replytoclient(userid?:"hi"))
        }
    }

}