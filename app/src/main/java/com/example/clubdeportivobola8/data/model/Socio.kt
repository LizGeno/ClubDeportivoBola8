package com.example.clubdeportivobola8.data.model

data class Socio(
    val id: Int = 0, // Es buena práctica incluir el ID
    val nombre: String,
    val apellido: String,
    val dni: String,
    val fechaNacimiento: String,
    val email: String
)