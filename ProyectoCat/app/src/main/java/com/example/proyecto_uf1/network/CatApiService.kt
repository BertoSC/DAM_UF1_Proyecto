package com.example.proyecto_uf1.network

import com.example.proyecto_uf1.models.CatImageResponse
import retrofit2.Call
import retrofit2.http.GET

interface CatApiService {
    @GET("images/search")
    fun getRandomCatImage(): Call<List<CatImageResponse>>
}