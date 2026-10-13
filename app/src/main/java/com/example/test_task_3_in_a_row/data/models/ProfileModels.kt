package com.example.test_task_3_in_a_row.data.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val iconBgColor: Color,
    val isCompleted: Boolean
)

data class ProfileState(
    val coins: Int = 0,
    val userName: String = "GEAR MASTER",
    val userLevel: Int = 0,
    val levelsCompleted: Int = 0,
    val bestScore: Int = 0,
    val totalCoinsEarned: Int = 0
)