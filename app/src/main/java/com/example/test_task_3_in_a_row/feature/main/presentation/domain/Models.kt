package com.example.test_task_3_in_a_row.feature.main.presentation.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import java.util.UUID

enum class CubeType(val color: Color, val icon: ImageVector) {
    GREEN(Color(0xFF67B75B), Icons.Default.RadioButtonUnchecked),
    PURPLE(Color(0xFF9B63E7), Icons.Default.Add),
    BLUE(Color(0xFF4A90E2), Icons.Default.ChangeHistory),
    RED(Color(0xFFD34A42), Icons.Default.Star),
    YELLOW(Color(0xFFE4BC4B), Icons.Default.Hexagon),
    PINK(Color(0xFFE26194), Icons.Default.Close)
}

data class LevelGoal(
    val type: CubeType,
    val count: Int
)

data class Cell(
    val row: Int,
    val col: Int,
    var type: CubeType,
    val isHinted: Boolean = false,
    val id: String = UUID.randomUUID().toString()
)