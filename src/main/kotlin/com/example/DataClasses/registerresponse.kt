package com.example.DataClasses

import kotlinx.serialization.Serializable

@Serializable
data class registerresponse (
    val email :String,
    val password :String,
    val fullname : String
    )

@Serializable
data class loginclass(
    val email: String,
    val password: String
)
