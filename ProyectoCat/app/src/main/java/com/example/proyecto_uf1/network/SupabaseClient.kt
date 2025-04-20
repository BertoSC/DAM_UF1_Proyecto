package com.example.proyecto_uf1.network

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

object SupabaseClient {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://dmtymvufqkppshkqluly.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRtdHltdnVmcWtwcHNoa3FsdWx5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQ5ODk4NTMsImV4cCI6MjA2MDU2NTg1M30.W11yey9XU2d9Eq5VtxnnZ60RntkJchrHKZLCqrgnsog"
    ){
        install(Auth)
        install(Postgrest)
        install(Storage)
        install(Realtime)
    }
}