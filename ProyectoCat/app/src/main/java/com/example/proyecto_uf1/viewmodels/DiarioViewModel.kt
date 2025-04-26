package com.example.proyecto_uf1.viewmodels
import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.proyecto_uf1.models.DiarioEntry
import com.example.proyecto_uf1.repositories.DiarioRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

// modelo de datos: extiende de AndroidViewModel y permite acceder al contexto global de la aplicación
// para operaciones de persistencia y manejo de recursos de sistema (archivos e imágenes)

class DiarioViewModel(application: Application) : AndroidViewModel(application) {

    private val _entradas = MutableLiveData<MutableList<DiarioEntry>>(mutableListOf())
    val entradas: LiveData<MutableList<DiarioEntry>> get() = _entradas

    private val repository = DiarioRepository(application)

    init {
        cargarEntradas()
    }

    fun agregarEntrada(entry: DiarioEntry) {
        viewModelScope.launch {
            repository.insertarEntrada(entry)
            cargarEntradas()
        }
    }

    fun eliminarEntrada(entry: DiarioEntry) {
        viewModelScope.launch {
            repository.borrarEntrada(entry)
            cargarEntradas()
        }
    }

    private fun cargarEntradas() {
        viewModelScope.launch {
            val lista = repository.obtenerEntradas()
            _entradas.postValue(lista.toMutableList())
        }
    }

    fun editarEntrada(entry: DiarioEntry) {
        viewModelScope.launch {
            repository.editarEntrada(entry)
            cargarEntradas()
        }
    }
}