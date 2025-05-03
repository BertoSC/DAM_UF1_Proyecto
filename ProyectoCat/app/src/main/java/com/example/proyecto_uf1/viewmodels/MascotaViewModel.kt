package com.example.proyecto_uf1.viewmodels

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.proyecto_uf1.models.Mascota
import com.example.proyecto_uf1.repositories.MascotaRepository
import kotlinx.coroutines.launch

class MascotaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MascotaRepository(application)

    private val _mascotas = MutableLiveData<List<Mascota>>(emptyList())
    val mascotas: LiveData<List<Mascota>> get() = _mascotas

    init {
        cargarMascotas()
    }

    fun cargarMascotas() {
        viewModelScope.launch {
            try {
                val lista = repository.obtenerMascotasUsuario()
                _mascotas.postValue(lista.toMutableList())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun agregarMascota(mascota: Mascota) {
        viewModelScope.launch {
            try {
                repository.insertarMascota(mascota)
                cargarMascotas()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun editarMascota(mascota: Mascota) {
        viewModelScope.launch {
            try {
                repository.editarMascota(mascota)
                cargarMascotas()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun eliminarMascota(mascota: Mascota) {
        viewModelScope.launch {
            mascota.id?.let {
                repository.eliminarMascota(it)
                cargarMascotas()
            }
        }
    }

    suspend fun subirImagen(uri: Uri): String? {
        return repository.subirImagenPerfil(uri)
    }
}
