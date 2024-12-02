package com.example.proyecto_uf1.viewmodels
import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.proyecto_uf1.models.DiarioEntry
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
    // almacenamos el contexto en una variable para la gestión de archivos internos y recursos
    private val context = application

    // LiveData se usa para gestionar las entradas
    //  _entradas  solo es accesible desde el ViewModel
    // entradas es accesible desde el Fragment Diario, pero solo para observar el estado de datos

    private val _entradas = MutableLiveData<MutableList<DiarioEntry>>(mutableListOf())
    val entradas: LiveData<MutableList<DiarioEntry>> get() = _entradas

    // variable que almacena la dirección del archivo json para persistencia de datos
    private val fileName = "diario_entries.json"

    init {
        // Carga las entradas almacenadas al iniciar la aplicación
        cargarEntradas()
    }

    // función para añadir una nueva entrada
    // recupera la lista actual mediante value y, si es null, devuelve una vacía
    // añade la entrada nueva y equipara la nueva lista a la variable _entradas
    // llama al método guardarEntradas() para actualizar el estado de la persistencia de datos

    fun agregarEntrada(entry: DiarioEntry) {
        val listaActual = _entradas.value ?: mutableListOf()
        listaActual.add(entry)
        _entradas.value = listaActual
        guardarEntradas()
    }

    // función para eliminar una entrada en concreto
    // recupera la lista, filtra la lista actual con todas las entradas
    // menos la que se desea eliminar, y actualiza el estado de datos de la APP

    fun eliminarEntrada(entry: DiarioEntry) {
        _entradas.value = _entradas.value?.filter { it != entry }?.toMutableList()
        guardarEntradas() // Guardar las entradas después de eliminar una
    }

    // Método para guardar las entradas en un archivo JSON
    // Ejecuta el código en un hilo de fondo para no bloquear la interfaz de usuario.
    // En este caso, permite ejecutar una tarea de entrada/salida de forma asíncrona
    // se usa para mantener un funcionamiento fluido sin bloquear el hilo principal
    // Obtiene la lista actual de entradas y la convierte a JSON usando Gson.
    // Escribe el JSON en el archivo, en el directorio interno de la aplicación (filesDir).

    private fun guardarEntradas() {
        viewModelScope.launch(Dispatchers.IO) {
            val listaActual = _entradas.value ?: mutableListOf()
            val json = Gson().toJson(listaActual)
            val archivo = File(context.filesDir, fileName)
            archivo.writeText(json)
        }
    }

    // Método para cargar las entradas desde un archivo JSON
    // convierte el archivo en una lista de objetos DiarioEntry mediante un TypeToken
    // se publica la lista cargada usando postvalue, actualizando el LiveData en segundo plano

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

    // primero, se obtiene el contexto de la APP para acceder al sistema de archivos
    // se utiliza la uri para abrir un flujo de entrada de datos
    // se crea un archivo nuevo para la imagen en el directorio de archivos de la APP
    // se realiza la copia desde el IS al OS y se devuelve la ruta absoluta de la IMG

    fun guardarImagenEnAlmacenamiento(uri: Uri): String? {
        return try {
            val context = getApplication<Application>()
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val fileName = "IMG_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)

            inputStream?.copyTo(outputStream)

            inputStream?.close()
            outputStream.close()

            file.absolutePath
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