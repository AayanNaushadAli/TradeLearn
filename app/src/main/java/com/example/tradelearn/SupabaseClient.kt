package com.example.tradelearn

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.json.Json

object SupabaseClient {
        private const val SUPABASE_URL = "https://sontgjqsqgpsilanfzug.supabase.co"
        private const val SUPABASE_KEY =
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNvbnRnanFzcWdwc2lsYW5menVnIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzE2ODMyMjEsImV4cCI6MjA4NzI1OTIyMX0.wXDi85goRD1N-fgUmIiArkrOXpldXQ-cjrvpf2hRZp8"

        val client =
                createSupabaseClient(supabaseUrl = SUPABASE_URL, supabaseKey = SUPABASE_KEY) {
                        install(Postgrest)
                        install(Auth)
                        defaultSerializer =
                                io.github.jan.supabase.serializer.KotlinXSerializer(
                                        Json {
                                                ignoreUnknownKeys = true
                                                coerceInputValues = true
                                        }
                                )
                }
}
