package com.example.proyecto_uf1.repositories


import com.example.proyecto_uf1.models.Anuncio
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

class AnuncioRepository (private val context: Context) {

    suspend fun insertarAnuncio(anuncio: Anuncio) = withContext(Dispatchers.IO) {
        SupabaseClient.supabase.from("entrada")
            .insert(anuncio)
    }


    suspend fun obtenerAnuncios(): List<Anuncio> = withContext(Dispatchers.IO) {
        SupabaseClient.supabase
            .from("anuncio")
            .select()
            .decodeList<Anuncio>()
    }

    suspend fun obtenerAnunciosUsuario(): List<Anuncio> = withContext(Dispatchers.IO){
            val userId = SupabaseClient.supabase.auth.currentUserOrNull()?.id ?: return@withContext emptyList()

            SupabaseClient.supabase
                .from("anuncio")
                .select {
                    filter {
                        eq("id_usuario", userId)
                   }
                }
                .decodeList<Anuncio>()

    }

    suspend fun eliminarAnuncio(anuncio: Anuncio) = withContext(Dispatchers.IO) {

        anuncio.id?.let{ id ->
            SupabaseClient.supabase.from("anuncio").delete {
                filter {
                    eq("id", id)
                }
            }


        }

    }

    suspend fun editarAnuncio(anuncio: Anuncio) = withContext(Dispatchers.IO) {
        anuncio.id?.let { id ->
            SupabaseClient.supabase.from("anuncio")
                .update(anuncio) {
                    filter {
                        eq("id", id)
                    }
                }
        }
    }

}