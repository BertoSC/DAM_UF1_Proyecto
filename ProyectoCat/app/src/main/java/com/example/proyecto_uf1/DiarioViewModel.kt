package com.example.proyecto_uf1
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DiarioViewModel : ViewModel() {


    // 1. _entradas (MutableLiveData) - solo accesible desde el ViewModel
    private val _entradas = MutableLiveData<MutableList<DiarioEntry>>(mutableListOf())

    // 2. entradas (LiveData) - accesible desde el Fragment (solo para observar)
    val entradas: LiveData<MutableList<DiarioEntry>> get() = _entradas

    fun agregarEntrada(entry: DiarioEntry) {
        // Agregar la nueva entrada a la lista y actualizar el LiveData
        val listaActual = _entradas.value ?: mutableListOf()
        listaActual.add(entry)
        _entradas.value = listaActual // Esto notifica a los observadores
    }

    // función para eliminar entradas
    // recupera la lisa de entradas, con value accedemos al contenido del live data
    // con el filter incluimos todo slos elementos que no sean iguales a la entrada actual
    //señalada

    fun eliminarEntrada(entry: DiarioEntry) {
        _entradas.value = _entradas.value?.filter { it != entry }?.toMutableList()
    }
}

