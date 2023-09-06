package com.example.Database

import org.ktorm.database.Database

object databaseconnextion {
    val database = Database.connect (
        url = "jdbc:mysql://localhost:3306/sunopodcast",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = "rootmysql"
    )
}