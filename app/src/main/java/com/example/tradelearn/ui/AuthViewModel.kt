package com.example.tradelearn.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradelearn.SupabaseClient
import io.github.jan.supabase.gotrue.*
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Represents the different states of our login process
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object OtpSent : AuthState() // Tells the UI to show the 6-digit code screen!
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    // 1. Sign Up Function
    fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                // This talks directly to your Supabase Auth server!
                SupabaseClient.client.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                    // We can even save their name directly into their Auth profile data!
                    this.data =
                            kotlinx.serialization.json.buildJsonObject {
                                put("full_name", kotlinx.serialization.json.JsonPrimitive(name))
                            }
                }

                // If it succeeds, Supabase automatically emails them the 6-digit code
                _authState.value = AuthState.OtpSent
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.localizedMessage ?: "Sign up failed")
            }
        }
    }

    // 2. Verify the 6-Digit Code
    fun verifyOtp(email: String, token: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                // Tells Supabase to check the 6-digit code
                SupabaseClient.client.auth.verifyEmailOtp(
                        type = OtpType.Email.SIGNUP,
                        email = email,
                        token = token
                )
                // If it works, tell the UI we are successful!
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.localizedMessage ?: "Invalid Code")
            }
        }
    }

    // 3. Log In (For returning users)
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                // This checks the encrypted password vault automatically!
                SupabaseClient.client.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }

                // If the password matches, they are instantly logged in!
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                // If wrong password, tell the UI to show an error
                _authState.value =
                        AuthState.Error(e.localizedMessage ?: "Login failed. Check your password.")
            }
        }
    }
}
