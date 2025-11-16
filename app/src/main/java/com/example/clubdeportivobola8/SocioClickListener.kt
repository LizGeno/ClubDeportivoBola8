package com.example.clubdeportivobola8

import com.example.clubdeportivobola8.data.model.Socio

interface SocioClickListener {
    fun onEditSocio(socio: Socio)
    fun onDeleteSocio(socio: Socio)
}