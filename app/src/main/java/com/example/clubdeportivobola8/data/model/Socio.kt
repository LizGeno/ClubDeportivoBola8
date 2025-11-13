package com.example.clubdeportivobola8.data.model
import java.util.Date
data class Socio(
    val id: Int = 0, // Es buena práctica incluir el ID
    val nombre: String,
    val apellido: String,
    val dni: String,
    val fechaNacimiento: Date,
    val email: String
)