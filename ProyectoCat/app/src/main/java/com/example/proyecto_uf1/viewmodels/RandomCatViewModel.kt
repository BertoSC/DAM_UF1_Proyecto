package com.example.proyecto_uf1.viewmodels

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto_uf1.network.CatApiService
import com.example.proyecto_uf1.models.CatImageResponse
import com.example.proyecto_uf1.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RandomCatViewModel : ViewModel() {

    private val _catImageUrl = MutableLiveData<String?>()
    val catImageUrl: LiveData<String?> get() = _catImageUrl

    private val _catBreedInfo = MutableLiveData<String?>()  // Información de la raza
    val catBreedInfo: LiveData<String?> get() = _catBreedInfo

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun obtenerGatito() {
        val catApi = RetrofitClient.instance.create(CatApiService::class.java)
        catApi.getRandomCatImage("live_bH0HP4J9ZCfi2Uf6eKkj18CjLSdL9V4D70FgqZ4tN6cwwEENiOU7aYX5P33wTOUw").enqueue(object : Callback<List<CatImageResponse>> {
            override fun onResponse(
                call: Call<List<CatImageResponse>>,
                response: Response<List<CatImageResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val catResponse = response.body()!![0]
                    _catImageUrl.value = catResponse.url

                    // Extraemos la información de la raza si existe
                    val breedInfo = catResponse.breeds?.firstOrNull()
                    breedInfo?.let {
                        _catBreedInfo.value = "Name: ${it.name}\n\n" +
                                "Origin: ${it.origin}\n\n" +
                                "Temperament: ${it.temperament}\n\n" +
                                "Description: ${it.description}"

                    }
                } else {
                    _error.value = "ERROR"
                }
            }

            override fun onFailure(call: Call<List<CatImageResponse>>, t: Throwable) {
                t.printStackTrace()
                _error.value = "NETWORK ERROR: ${t.message}"
            }
        })
    }
}




/*
package com.example.proyecto_uf1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto_uf1.network.CatApiService
import com.example.proyecto_uf1.models.CatImageResponse
import com.example.proyecto_uf1.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RandomCatViewModel : ViewModel() {

    private val _catImageUrl = MutableLiveData<String?>()
    val catImageUrl: LiveData<String?> get() = _catImageUrl

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun obtenerGatito() {
        val catApi = RetrofitClient.instance.create(CatApiService::class.java)
        catApi.getRandomCatImage().enqueue(object : Callback<List<CatImageResponse>> {
            override fun onResponse(
                call: Call<List<CatImageResponse>>,
                response: Response<List<CatImageResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val catImageUrl = response.body()!![0].url
                    _catImageUrl.value = catImageUrl
                } else {
                    _error.value = "ERROR"
                }
            }

            override fun onFailure(call: Call<List<CatImageResponse>>, t: Throwable) {
                t.printStackTrace()
                _error.value = "NETWORK ERROR: ${t.message}"
            }
        })
    }
}
*/