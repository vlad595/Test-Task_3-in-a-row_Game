package com.example.test_task_3_in_a_row.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.test_task_3_in_a_row.feature.home.presentation.MainScreen
import com.example.test_task_3_in_a_row.feature.levels.presentation.LevelsScreen
import com.example.test_task_3_in_a_row.feature.main.presentation.GameScreen
import com.example.test_task_3_in_a_row.feature.main.presentation.domain.generateLevels

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val levels = remember { generateLevels() }

    // Імітація бази даних пройдених рівнів (для прикладу)
    val passedLevels = mapOf(1 to 3, 2 to 3, 3 to 2, 4 to 1)
    val currentMoney = 1250

    NavHost(navController = navController, startDestination = Screen.Main.route) {

        // 1. Головний екран
        composable(Screen.Main.route) {
            MainScreen(
                // Передаємо функцію навігації як параметр
                onPlayClick = { navController.navigate(Screen.Levels.route) }
            )
        }

        // 2. Екран списку рівнів
        composable(Screen.Levels.route) {
            LevelsScreen(
                money = currentMoney,
                passedLevels = passedLevels,
                onLevelClick = { levelId ->
                    navController.navigate(Screen.Game.createRoute(levelId))
                },
                onBackClick = { navController.popBackStack() } // Повернення назад
            )
        }

        // 3. Екран самої гри
        composable(
            route = Screen.Game.route,
            arguments = listOf(navArgument("levelId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Отримуємо ID рівня з параметрів (або 1 за замовчуванням)
            val levelId = backStackEntry.arguments?.getInt("levelId") ?: 1

            // Знаходимо конфігурацію саме для цього рівня
            val levelConfig = levels.find { it.id == levelId } ?: levels.first()

            GameScreen(
                levelNumber = levelConfig.id,
                money = currentMoney,
                moves = levelConfig.moves,
                goals = levelConfig.goals,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}