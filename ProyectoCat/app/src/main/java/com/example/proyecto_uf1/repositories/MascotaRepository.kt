package com.example.proyecto_uf1.repositories
import android.content.Context
import android.net.Uri
import com.example.proyecto_uf1.models.Mascota
import com.example.proyecto_uf1.network.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MascotaRepository(private val context: Context) {

    suspend fun insertarMascota(mascota: Mascota) = withContext(Dispatchers.IO) {
        SupabaseClient.supabase.from("mascota").insert(mascota)
    }

    suspend fun obtenerMascotasUsuario(): List<Mascota> = withContext(Dispatchers.IO) {
        val userId = SupabaseClient.supabase.auth.currentUserOrNull()?.id ?: return@withContext emptyList()
        SupabaseClient.supabase
            .from("mascota")
            .select {
                filter { eq("id_usuario", userId) }
            }
            .decodeList<Mascota>()
    }

    suspend fun editarMascota(mascota: Mascota) = withContext(Dispatchers.IO) {
        mascota.id?.let { id ->
            SupabaseClient.supabase.from("mascota").update(mascota) {
                filter { eq("id", id) }
            }
        }
    }

    suspend fun eliminarMascota(id: String) = withContext(Dispatchers.IO) {
        SupabaseClient.supabase.from("mascota").delete {
            filter { eq("id", id) }
        }
    }

    suspend fun subirImagenPerfil(uri: Uri): String? = withContext(Dispatchers.IO) {
        return@withContext try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val nombre = "GATO_${System.currentTimeMillis()}.jpg"

            inputStream?.let {
                val bytes = it.readBytes()
                SupabaseClient.supabase.storage.from("catstorage").upload(nombre, bytes)
                SupabaseClient.supabase.storage.from("catstorage").publicUrl(nombre)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
