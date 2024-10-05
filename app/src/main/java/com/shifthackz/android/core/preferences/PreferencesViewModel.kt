package com.shifthackz.android.core.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PreferencesViewModel(
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(State())
    val uiState = _uiState.asStateFlow()

    init {
        loadPreferences()
    }

    fun setName(value: String) {
        preferencesManager.name = value
        loadPreferences()
    }

    fun setStringSet(value: Set<String>) {
        preferencesManager.set = value
        loadPreferences()
    }

    fun setLong(value: Long) {
        preferencesManager.long = value
        loadPreferences()
    }

    fun setCount(value: Int) {
        preferencesManager.count = value
        loadPreferences()
    }

    fun setPoint(value: Float) {
        preferencesManager.point = value
        loadPreferences()
    }

    private fun loadPreferences() {
        val state = State(
            name = preferencesManager.name,
            set = preferencesManager.set,
            count = preferencesManager.count,
            point = preferencesManager.point,
            long = preferencesManager.long,
        )
        viewModelScope.launch {
            _uiState.emit(state)
        }
    }

    data class State(
        val name: String = "",
        val set: Set<String> = emptySet(),
        val count: Int = 0,
        val point: Float = 0.0f,
        val long: Long = 0L,
    )
}
