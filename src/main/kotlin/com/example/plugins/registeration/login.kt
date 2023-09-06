package com.example.plugins.registeration

import com.example.DataClasses.loginclass
import com.example.DataClasses.replytoclient
import com.example.Database.Registeerdentity
import com.example.Database.databaseconnextion
import com.example.JWTtokken.TokenConfig
import com.example.JWTtokken.TokengnerateService
import com.example.JWTtokken.tokenclaims
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.*
import org.mindrot.jbcrypt.BCrypt

fun Route.login( tokengnerateService: TokengnerateService, tokenConfig: TokenConfig){
    val data = databaseconnextion.database
    post ("/login"){
        val rerc =call.receive<loginclass>()
        val username = rerc.email
        val pass = rerc.password
        val check = data.from(Registeerdentity).select().where(Registeerdentity.email eq username).map {
            it[Registeerdentity.password]!!
        }.firstOrNull()
        val checkemail = data.from(Registeerdentity).select().where(Registeerdentity.email eq username).map {
            it[Registeerdentity.email]!!
        }.firstOrNull()
        if (checkemail!=null){
            val checkpass = BCrypt.checkpw(pass,check)
            if (checkpass){
                val token = tokengnerateService.generate(
                    config = tokenConfig,
                    tokenclaims(
                        name = "Useremail",
                        value = username
                    )
                )

                call.respond(HttpStatusCode.OK,replytoclient(token))
            }else{
                call.respond(HttpStatusCode.Conflict,replytoclient("Incorrect Password"))
            }

        }else{
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}