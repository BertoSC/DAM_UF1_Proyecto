package com.example.proyecto_uf1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto_uf1.R

class DoctorViewModel : ViewModel() {
    private val _edad = MutableLiveData<Int>()
    val edad: LiveData<Int> get() = _edad

    fun setEdad(nuevaEdad: Int) {
        _edad.value = nuevaEdad
    }

    fun asignarId(): Int {
        val edadActual = _edad.value ?: 0
        return when {
            edadActual < 1 -> R.string.cachorro
            edadActual in 1..3 -> R.string.joven
            edadActual in 4..11 -> R.string.adulto
            else -> R.string.senior
        }
    }

    fun asignarImg():Int{
        val edadActual = _edad.value ?: 0
        return when {
            edadActual < 1 -> R.drawable.cachorro
            edadActual in 1..3 -> R.drawable.joven
            edadActual in 4..11 -> R.drawable.adulto
            else -> R.drawable.senior
        }
    }
}