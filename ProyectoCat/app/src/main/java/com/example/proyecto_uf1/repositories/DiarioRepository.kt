package com.example.proyecto_uf1.repositories

import android.content.Context
import android.net.Uri
import com.example.proyecto_uf1.models.DiarioEntry
import com.example.proyecto_uf1.network.SupabaseClient
import com.example.proyecto_uf1.network.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.storage.storage
import io.ktor.utils.io.jvm.javaio.toByteReadChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DiarioRepository(private val context: Context) {

    suspend fun subirImagen(uri: Uri): String? = withContext(Dispatchers.IO) {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val nombre = "IMG_${System.currentTimeMillis()}.jpg"

            inputStream?.let {
                val byteArray = inputStream.readBytes()
                var bucket = SupabaseClient.supabase.storage.from("catstorage")
                bucket.upload(nombre, byteArray)

                return@withContext supabase.storage.from("catstorage").publicUrl(nombre)

            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun insertarEntrada(entry: DiarioEntry) = withContext(Dispatchers.IO) {
        SupabaseClient.supabase.from("entrada")
            .insert(entry)
    }

    suspend fun obtenerEntradas(): List<DiarioEntry> = withContext(Dispatchers.IO) {
            val userId = SupabaseClient.supabase.auth.currentUserOrNull()?.id ?: return@withContext emptyList()

            SupabaseClient.supabase
                .from("entrada")
                .select {
                    filter {
                        eq("id_usuario", userId)

                    }
                    order(column = "fecha", order = Order.DESCENDING)
                }
                .decodeList<DiarioEntry>()
        }

    suspend fun borrarEntrada(entry: DiarioEntry) = withContext(Dispatchers.IO) {
        try {
            entry.imagenUri?.let { eliminarImagen(it) }

            entry.id?.let { id ->
                SupabaseClient.supabase.from("entrada")
                    .delete {
                        filter {
                            eq("id", id)
                        }
                    }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun editarEntrada(entry: DiarioEntry) = withContext(Dispatchers.IO) {
        SupabaseClient.supabase.from("entrada")
            .update(entry) {
                filter {
                    eq("id", entry.id ?: "")
                }
            }
    }

    suspend fun eliminarImagen(imagenUrl: String) = withContext(Dispatchers.IO) {
        try {

            val nombreArchivo = imagenUrl.substringAfterLast("/")

            SupabaseClient.supabase.storage.from("catstorage")
                .delete(listOf(nombreArchivo))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

