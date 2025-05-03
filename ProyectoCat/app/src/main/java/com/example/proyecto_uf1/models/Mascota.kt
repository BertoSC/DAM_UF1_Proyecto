package com.example.proyecto_uf1.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class Mascota(
    val id: String? = null,
    @SerialName("id_usuario")
    val idUsuario: String,
    val nombre: String,
    val edad: Int?,
    val raza: String?,
    val vacunas: String?,
    val alimentacion: String?,
    val medicacion: String?,
    @SerialName("imagen_gato_url")
    val imagenGatoUrl: String

) : Parcelable