package com.example.tradelearn.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradelearn.SupabaseClient
import com.example.tradelearn.data.LessonItem
import com.example.tradelearn.data.LessonRepository
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.filter.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 1. This represents the three states our screen0 can be in
sealed class LessonUiState {
    object Loading : LessonUiState()
    data class Success(val lessonItems: List<LessonItem>) : LessonUiState()
    data class Error(val message: String) : LessonUiState()
}

class LessonViewModel : ViewModel() {

    // Initialize our repository to talk to Supabase
    private val repository = LessonRepository()

    // --- STATE MANAGEMENT ---

    // The main UI state (Loading vs Success)
    private val _uiState = MutableStateFlow<LessonUiState>(LessonUiState.Loading)
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    // Tracks which card the user is currently looking at
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    // The Duolingo-style Health System
    private val _hearts = MutableStateFlow(5)
    val hearts: StateFlow<Int> = _hearts.asStateFlow()

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver.asStateFlow()

    private val _isLessonFinished = MutableStateFlow(false)
    val isLessonFinished: StateFlow<Boolean> = _isLessonFinished.asStateFlow()

    // --- ACTIONS ---

    // Called when the user clicks "Start Lesson" on Chapter 1
    fun loadLesson(chapterId: Int) {
        viewModelScope.launch {
            _uiState.value = LessonUiState.Loading

            // Fetch the parsed JSON from our Supabase Repository
            val result = repository.getLessonForChapter(chapterId)

            result.fold(
                    onSuccess = { items ->
                        if (items.isNotEmpty()) {
                            _uiState.value = LessonUiState.Success(items)
                            resetGame()
                        } else {
                            _uiState.value = LessonUiState.Error("Lesson is empty.")
                        }
                    },
                    onFailure = { error ->
                        _uiState.value = LessonUiState.Error("${error.message}")
                    }
            )
        }
    }

    // Called when the user clicks "Next" or finishes a question
    fun onNextCard() {
        val currentState = _uiState.value
        if (currentState is LessonUiState.Success) {
            // If there are more cards, move to the next one
            if (_currentIndex.value < currentState.lessonItems.size - 1) {
                _currentIndex.value += 1
            } else {
                // If it was the last card, the user beat the level!
                _isLessonFinished.value = true
                // TODO: Here we will later trigger a Supabase write to give the user XP
            }
        }
    }

    // Called if they tap the wrong multiple-choice option
    fun onWrongAnswer() {
        _hearts.value -= 1
        if (_hearts.value <= 0) {
            _isGameOver.value = true
        }
    }

    @kotlinx.serialization.Serializable
    data class UserProfileUpdate(@kotlinx.serialization.SerialName("total_xp") val totalXp: Int)

    fun finishLesson(userId: String) {
        viewModelScope.launch {
            try {
                // 1. Get the table reference
                val table = SupabaseClient.client.postgrest["user_profiles"]

                // 2. Perform the update with the proper filter block
                table.update(update = { set("total_xp", 50) }) {
                    // This is where the 'eq' and 'filter' logic lives
                    filter { eq("id", userId) }
                }

                _isLessonFinished.value = true
            } catch (e: Exception) {
                android.util.Log.e("DB_ERROR", "Failed to save progress", e)
            }
        }
    }

    private fun resetGame() {
        _currentIndex.value = 0
        _hearts.value = 5
        _isGameOver.value = false
        _isLessonFinished.value = false
    }
}
