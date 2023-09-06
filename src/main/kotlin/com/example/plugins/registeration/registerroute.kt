package com.example.plugins.registeration

import com.example.DataClasses.registerresponse
import com.example.DataClasses.replytoclient
import com.example.Database.Registeerdentity
import com.example.Database.databaseconnextion
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.*
import org.mindrot.jbcrypt.BCrypt

fun Route.registeration(){
    val db = databaseconnextion.database
    post("register") {
        val recieved = call.receive<registerresponse>()
        val username = recieved.email.toLowerCase()
        val fullname = recieved.fullname
        val password = BCrypt.hashpw(recieved.password,BCrypt.gensalt())
         val userifexist  = db.from(Registeerdentity).select().where(
             Registeerdentity.email eq  username
         ).map {
             it[Registeerdentity.email]
         }.firstOrNull()

        if (userifexist == null){
            val r = db.insert(Registeerdentity){
                set(it.email , username)
                set(it.fullname,fullname)
                set(it.password,password)
            }

            if (r!=1){
                call.respond(HttpStatusCode.BadRequest,replytoclient("Unkown Error Occured Unable to Register the user"))
            }else{

                call.respond(HttpStatusCode.OK,replytoclient("User Registered"))
            }
        }else{
            call.respond(HttpStatusCode.Conflict,replytoclient("User Already Exist"))
        }

    }
}