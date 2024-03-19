package com.example.lab3app.data

import java.util.UUID

data class University(
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var city: String = ""
)
