package com.example.proyecto_uf1.network

import com.example.proyecto_uf1.models.CatImageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface CatApiService {
    @GET("images/search?has_breeds=1")
    fun getRandomCatImage(
        @Header("x-api-key") apiKey: String = "live_bH0HP4J9ZCfi2Uf6eKkj18CjLSdL9V4D70FgqZ4tN6cwwEENiOU7aYX5P33wTOUw"
    ): Call<List<CatImageResponse>>
}