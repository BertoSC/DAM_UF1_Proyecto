package com.example.proyecto_uf1

import android.net.Uri
// molde clase "pollo" para los datos básicos del diario (pendiente de añadir más)


data class DiarioEntry(
    val titulo: String,
    val texto: String,
    val fecha: String,
    val imagenUri: String? = null
)
