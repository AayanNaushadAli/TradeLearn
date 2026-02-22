package com.example.tradelearn.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradelearn.data.Chapter
import com.example.tradelearn.data.LessonRepository
import com.example.tradelearn.data.Module
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = LessonRepository()

    private val _modules = MutableStateFlow<List<Module>>(emptyList())
    val modules = _modules.asStateFlow()

    private val _chapters = MutableStateFlow<List<Chapter>>(emptyList())
    val chapters = _chapters.asStateFlow()

    init {
        loadCurriculum()
    }

    private fun loadCurriculum() {
        viewModelScope.launch {
            _modules.value = repository.getAllModules()
            _chapters.value = repository.getAllChapters()
        }
    }
}
