package com.example.proyecto_uf1.network

import com.example.proyecto_uf1.models.CatImageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface CatApiService {
    @GET("images/search?has_breeds=1")
    fun getRandomCatImage(
        @Header("x-api-key") apiKey: String = APIKEY"
    ): Call<List<CatImageResponse>>
}
