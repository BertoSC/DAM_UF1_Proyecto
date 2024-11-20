package com.example.proyecto_uf1
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
}