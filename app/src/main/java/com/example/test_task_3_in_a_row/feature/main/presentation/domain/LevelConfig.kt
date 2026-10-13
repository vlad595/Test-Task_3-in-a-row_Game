package com.example.test_task_3_in_a_row.feature.main.presentation.domain

import kotlin.random.Random

// Модель нашого рівня
data class LevelConfig(
    val id: Int,
    val moves: Int,
    val goals: List<LevelGoal>
)

// Функція генерації 100 рівнів
fun generateLevels(): List<LevelConfig> {
    return (1..100).map { level ->
        // Чим вищий рівень, тим більше типів кубиків треба зібрати (від 1 до 3)
        val numGoalTypes = when {
            level < 10 -> 1
            level < 20 -> 2
            else -> 3
        }

        // Базові ходи: 15. З кожним 5-м рівнем додаємо ще 1 хід.
        val moves = 15 + (level / 5)

        // Загальна кількість кубиків, які треба зібрати (зростає з рівнями)
        val totalCubesToCollect = 10 + (level / 2)

        val availableTypes = CubeType.values().toList().shuffled(Random(level))

        val goals = (0 until numGoalTypes).map { i ->
            LevelGoal(
                type = availableTypes[i],
                // Ділимо загальну кількість потрібних кубиків між вибраними типами
                count = totalCubesToCollect / numGoalTypes
            )
        }

        LevelConfig(id = level, moves = moves, goals = goals)
    }
}