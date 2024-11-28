package com.example.proyecto_uf1

import retrofit2.Call
import retrofit2.http.GET

interface CatApiService {
    @GET("images/search")
    fun getRandomCatImage(): Call<List<CatImageResponse>>
}