package com.example.proyecto_uf1.models


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class Anuncio(
    val id: String? = null,
    @SerialName("id_usuario")
    val idUsuario: String,
    val texto: String,
    val localidad: String
) : Parcelable