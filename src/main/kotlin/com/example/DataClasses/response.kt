package com.example.DataClasses

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class response(
    val key:List<String>
)

@Serializable
data class replytoclient(
    val message :String
)
