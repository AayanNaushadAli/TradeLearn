package com.example.tradelearn.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradelearn.SupabaseClient
import com.example.tradelearn.data.UserProfile
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.filter.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile = _userProfile.asStateFlow()

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            try {
                // 1. Get the current secure User ID from the Auth vault
                val currentUser = SupabaseClient.client.auth.currentUserOrNull()

                if (currentUser != null) {
                    // 2. Ask the database for the matching profile row
                    val profile =
                            SupabaseClient.client.postgrest["user_profiles"].select {
                                        filter { eq("id", currentUser.id) }
                                    }
                                    .decodeSingleOrNull<UserProfile>()

                    _userProfile.value = profile
                }
            } catch (e: Exception) {
                android.util.Log.e("PROFILE_ERROR", "Failed to fetch profile", e)
            }
        }
    }
}
