package com.example.test_task_3_in_a_row.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_task_3_in_a_row.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val coins: Int = 1250,
    val isMusicEnabled: Boolean = true,
    val isSoundEffectsEnabled: Boolean = true,
    val isVibrationEnabled: Boolean = false,
    val isNotificationsEnabled: Boolean = true,
    val language: String = "EN"
)

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: GameRepository) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        repository.coinsFlow,
        repository.musicEnabledFlow,
        repository.soundEnabledFlow,
        repository.vibrationEnabledFlow,
        repository.notificationsEnabledFlow,
        repository.languageFlow
    ) { args ->
        SettingsUiState(
            coins = args[0] as Int,
            isMusicEnabled = args[1] as Boolean,
            isSoundEffectsEnabled = args[2] as Boolean,
            isVibrationEnabled = args[3] as Boolean,
            isNotificationsEnabled = args[4] as Boolean,
            language = args[5] as String
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsUiState())

    fun toggleMusic() = viewModelScope.launch { repository.setMusic(!uiState.value.isMusicEnabled) }
    fun toggleSoundEffects() = viewModelScope.launch { repository.setSound(!uiState.value.isSoundEffectsEnabled) }
    fun toggleVibration() = viewModelScope.launch { repository.setVibration(!uiState.value.isVibrationEnabled) }
    fun toggleNotifications() = viewModelScope.launch { repository.setNotifications(!uiState.value.isNotificationsEnabled) }
    fun changeLanguage(newLang: String = if (uiState.value.language == "EN") "UA" else "EN") = viewModelScope.launch { repository.setLanguage(newLang) }
}