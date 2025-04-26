package com.example.proyecto_uf1.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


// molde clase "pollo" para los datos básicos del diario (pendiente de añadir más)

/* // versión json
data class DiarioEntry(
    val titulo: String?,
    val texto: String?,
    val fecha: String?,
    val imagenUri: String? = null
)*/

// Versión para supabase

@Serializable
@Parcelize
data class DiarioEntry(
    val id: String? = null,
    @SerialName("id_usuario")
    val idUsuario: String,
    val titulo: String,
    val texto: String,
    val fecha: String? = null,
    @SerialName("imagen_uri")
    val imagenUri: String? = null
) : Parcelable