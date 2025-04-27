package com.example.proyecto_uf1.viewmodels
import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.proyecto_uf1.models.Anuncio
import com.example.proyecto_uf1.models.DiarioEntry
import com.example.proyecto_uf1.repositories.AnuncioRepository
import com.example.proyecto_uf1.repositories.DiarioRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class AnuncioViewModel (application: Application) : AndroidViewModel(application) {

    private val repository = AnuncioRepository(application)

    private val _anuncios = MutableLiveData<List<Anuncio>>(emptyList())
    val anuncios: LiveData<List<Anuncio>> get() = _anuncios

    init{
        cargarAnuncios()
    }

    fun cargarAnuncios() {
        viewModelScope.launch {
            try {
                val lista = repository.obtenerAnuncios()
                _anuncios.value = lista
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun agregarAnuncio(anuncio: Anuncio) {
        viewModelScope.launch {
            try {
                repository.insertarAnuncio(anuncio)
                cargarAnuncios()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun eliminarAnuncio(anuncio: Anuncio) {
        viewModelScope.launch {
            try {
                repository.eliminarAnuncio(anuncio)
                cargarAnuncios()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun editarAnuncio(anuncio: Anuncio) {
        viewModelScope.launch {
            try {
                repository.editarAnuncio(anuncio)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}