package com.example.test_task_3_in_a_row.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_task_3_in_a_row.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LevelsViewModel @Inject constructor(private val repository: GameRepository) : ViewModel() {
    val coins: StateFlow<Int> = repository.coinsFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 1250)
    val completedLevels: StateFlow<Int> = repository.completedLevelsFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
}