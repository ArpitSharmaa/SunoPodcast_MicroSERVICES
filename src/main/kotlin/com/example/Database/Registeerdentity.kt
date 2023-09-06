package com.example.Database

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text

object Registeerdentity: Table<Nothing>("registereduser") {
    val id = int("id").primaryKey()
    val email = text("email")
    val password = text("password")
    val fullname = text("fullname")

}