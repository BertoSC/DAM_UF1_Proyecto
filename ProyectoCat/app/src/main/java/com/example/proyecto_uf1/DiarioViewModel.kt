package com.example.proyecto_uf1
import android.app.Application
import android.net.Uri
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class DiarioViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application

    // LiveData para gestionar las entradas
    private val _entradas = MutableLiveData<MutableList<DiarioEntry>>(mutableListOf())
    val entradas: LiveData<MutableList<DiarioEntry>> get() = _entradas

    private val fileName = "diario_entries.json" // Nombre del archivo para la persistencia

    init {
        // Cargar las entradas almacenadas al iniciar la aplicación
        cargarEntradas()
    }

    fun agregarEntrada(entry: DiarioEntry) {
        val listaActual = _entradas.value ?: mutableListOf()
        listaActual.add(entry)
        _entradas.value = listaActual
        guardarEntradas() // Guardar las entradas después de añadir una nueva
    }

    fun eliminarEntrada(entry: DiarioEntry) {
        _entradas.value = _entradas.value?.filter { it != entry }?.toMutableList()
        guardarEntradas() // Guardar las entradas después de eliminar una
    }

    // Método para guardar las entradas en un archivo JSON
    private fun guardarEntradas() {
        viewModelScope.launch(Dispatchers.IO) {
            val listaActual = _entradas.value ?: mutableListOf()
            val json = Gson().toJson(listaActual)
            val archivo = File(context.filesDir, fileName)
            archivo.writeText(json)
        }
    }


    // Método para cargar las entradas desde un archivo JSON
    private fun cargarEntradas() {
        viewModelScope.launch(Dispatchers.IO) {
            val archivo = File(context.filesDir, fileName)
            if (archivo.exists()) {
                val json = archivo.readText()
                val tipoLista = object : TypeToken<MutableList<DiarioEntry>>() {}.type
                val listaCargada: MutableList<DiarioEntry> = Gson().fromJson(json, tipoLista)
                _entradas.postValue(listaCargada)
            }
        }
    }

    fun guardarImagenEnAlmacenamiento(uri: Uri): String? {
        return try {
            val context = getApplication<Application>() // Obtiene el contexto del Application
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val fileName = "IMG_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)

            // Copiar datos del InputStream al OutputStream
            inputStream?.copyTo(outputStream)

            inputStream?.close()
            outputStream.close()

            file.absolutePath // Devuelve la ruta completa donde se guardó la imagen
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}












/*

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

*/