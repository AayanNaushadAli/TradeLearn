package com.example.tradelearn.data

import com.example.tradelearn.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LessonRepository {

    // Fetches the lesson for a specific chapter off the main UI thread
    suspend fun getLessonForChapter(chapterId: Int): Result<List<LessonItem>> {
        return withContext(Dispatchers.IO) {
            try {
                // 1. Query the 'lessons' table
                val result =
                        SupabaseClient.client.postgrest["lessons"].select {
                                    filter { eq("chapter_id", chapterId) }
                                }
                                .decodeSingleOrNull<LessonRow>()

                // 2. Return the decoded JSON array
                if (result?.contentPayload != null) {
                    Result.success(result.contentPayload)
                } else {
                    Result.failure(
                            Exception("Empty row or missing contentPayload for chapter $chapterId")
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }
    suspend fun getAllModules(): List<Module> {
        return withContext(Dispatchers.IO) {
            SupabaseClient.client.postgrest["modules"].select().decodeList<Module>()
        }
    }

    suspend fun getAllChapters(): List<Chapter> {
        return withContext(Dispatchers.IO) {
            SupabaseClient.client.postgrest["chapters"].select().decodeList<Chapter>()
        }
    }
}
