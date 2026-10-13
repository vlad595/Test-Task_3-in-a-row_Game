package com.example.test_task_3_in_a_row.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsUiState(
    val coins: Int = 1250,
    val isMusicEnabled: Boolean = true,
    val isSoundEffectsEnabled: Boolean = true,
    val isVibrationEnabled: Boolean = false,
    val isNotificationsEnabled: Boolean = true,
    val language: String = "EN"
)

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun toggleMusic() {
        _uiState.update { it.copy(isMusicEnabled = !it.isMusicEnabled) }
    }

    fun toggleSoundEffects() {
        _uiState.update { it.copy(isSoundEffectsEnabled = !it.isSoundEffectsEnabled) }
    }

    fun toggleVibration() {
        _uiState.update { it.copy(isVibrationEnabled = !it.isVibrationEnabled) }
    }

    fun toggleNotifications() {
        _uiState.update { it.copy(isNotificationsEnabled = !it.isNotificationsEnabled) }
    }

    fun changeLanguage(newLang: String = "EN") {
        _uiState.update { it.copy(language = newLang) }
    }

    fun restorePurchases() {
        println("Restoring purchases...")
    }
}