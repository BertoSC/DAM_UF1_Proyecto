package com.example.proyecto_uf1.network

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

object SupabaseClient {

    val supabase = createSupabaseClient(
        supabaseUrl = "URL",
        supabaseKey = "API KEY"
    ){
        install(Auth)
        install(Postgrest)
        install(Storage)
        install(Realtime)
    }
}

