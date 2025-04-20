package com.example.proyecto_uf1.models

data class CatImageResponse(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<CatBreed>?
)

data class CatBreed(
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String
)

