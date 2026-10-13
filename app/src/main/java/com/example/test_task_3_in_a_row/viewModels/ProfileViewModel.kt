package com.example.test_task_3_in_a_row.viewModels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_task_3_in_a_row.data.models.Achievement
import com.example.test_task_3_in_a_row.data.models.ProfileState
import com.example.test_task_3_in_a_row.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlin.collections.emptyList
import kotlinx.coroutines.flow.*

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: GameRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = combine(
        repository.coinsFlow,
        repository.completedLevelsFlow,
        repository.bestScoreFlow
    ) { coins, levels, bestScore ->
        ProfileState(
            coins = coins,
            userName = "GEAR MASTER",
            userLevel = if (levels == 0) 1 else levels,
            levelsCompleted = levels,
            bestScore = bestScore,
            totalCoinsEarned = coins
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileState())

    private val _allAchievements = MutableStateFlow(
        listOf(
            Achievement("1", "First Match", "Make your first match", Icons.Default.Star, Color(0xFFD64E4E), isCompleted = true),
            Achievement("2", "Combo King", "Chain 5 combos", Icons.Default.ChangeHistory, Color(0xFFD64E4E), isCompleted = true),
            Achievement("3", "Flawless", "Clear with moves left", Icons.Default.RadioButtonUnchecked, Color(0xFF4699C8), isCompleted = false),
            Achievement("4", "Marathon", "Play 30 levels", Icons.Default.Hexagon, Color(0xFFA57CF0), isCompleted = true), // Зробив виконаним для прикладу

            Achievement("5", "Novice", "Complete 10 levels", Icons.Default.CheckCircle, Color(0xFF62B056), isCompleted = true),
            Achievement("6", "Expert", "Complete 50 levels", Icons.Default.CheckCircle, Color(0xFFE2B441), isCompleted = false),
            Achievement("7", "Master", "Complete 100 levels", Icons.Default.Star, Color(0xFFE7658B), isCompleted = false),

            Achievement("8", "Big Bang", "Match 5 blocks at once", Icons.Default.AutoAwesome, Color(0xFFE2B441), isCompleted = true),
            Achievement("9", "Survivor", "Win with 0 moves left", Icons.Default.Warning, Color(0xFFD64E4E), isCompleted = false),
            Achievement("10", "Brainiac", "Win a level without hints", Icons.Default.Psychology, Color(0xFF4699C8), isCompleted = false),

            Achievement("11", "First Blood", "Buy your first item in Shop", Icons.Default.ShoppingCart, Color(0xFF62B056), isCompleted = true),
            Achievement("12", "Collector", "Unlock all items in Shop", Icons.Default.AllInclusive, Color(0xFFA57CF0), isCompleted = false),
            Achievement("13", "Rich", "Collect 10,000 coins", Icons.Default.MonetizationOn, Color(0xFFE2B441), isCompleted = false)
        )
    )
    private val _achievements = MutableStateFlow(
        _allAchievements.value.filter { it.isCompleted }
    )
    val achievements: StateFlow<List<Achievement>> = repository.unlockedAchievementsFlow
        .map { unlockedIds ->
            _allAchievements.value.filter { unlockedIds.contains(it.id) }
                .map { it.copy(isCompleted = true) }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()) // <--- Додано закриваючу дужку

    fun unlockAchievement(id: String) {
        val updatedList = _allAchievements.value.map {
            if (it.id == id) it.copy(isCompleted = true) else it
        }
        _allAchievements.value = updatedList

        _achievements.value = updatedList.filter { it.isCompleted }
    }
}