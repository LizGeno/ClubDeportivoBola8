package com.example.clubdeportivobola8

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cuotas")
data class Cuota(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreSocio: String,
    val actividad: String,
    val fechaVencimiento: String,
    val estado: String
)
