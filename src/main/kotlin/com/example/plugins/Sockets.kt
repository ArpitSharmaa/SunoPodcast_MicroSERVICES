package com.example.plugins

import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration
import io.ktor.server.application.*
import io.ktor.server.routing.*
import java.util.concurrent.ConcurrentHashMap

fun Application.configureSockets(livepodcasts:ConcurrentHashMap<String,String>) {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        val clients = ConcurrentHashMap<WebSocketSession,Unit>()
        webSocket("/podcast/{podcastname}") { // websocketSession

            val z = call.parameters["podcastname"]
            livepodcasts[z!!]=z
           try {

               clients[this] = Unit

               while (true) {

                   val frame = incoming.receive() as? Frame.Binary ?: break

                   val audioBuffer = frame.data

                   clients.filter { it.key != this }.forEach { client ->
                       try {
                           client.key.outgoing.send(Frame.Binary(true, audioBuffer))
                       } catch (ex: Exception) {
                           client.key.close()
                       }
                   }
               }
           }catch (_:Exception){

           }finally {
               if (this.closeReason != null){
                   livepodcasts.remove(z)
               }
           }

        }

        webSocket ("/karoke"){
            try {

                clients[this] = Unit

                while (true) {

                    val frame = incoming.receive() as? Frame.Binary ?: break


                    val audioBuffer = frame.data


                    clients.filter { it.key != this }.forEach { client ->
                        try {
                            client.key.outgoing.send(Frame.Binary(true, audioBuffer))
                        } catch (ex: Exception) {

                            client.key.close()
                        }
                    }
                }
            }catch (_:Exception){

            }finally {

            }

        }


    }
}
