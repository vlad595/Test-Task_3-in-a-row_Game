package com.example.test_task_3_in_a_row.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.test_task_3_in_a_row.ui.screens.home.MainScreen
import com.example.test_task_3_in_a_row.ui.screens.levels.LevelsScreen
import com.example.test_task_3_in_a_row.ui.screens.main.GameScreen
import com.example.test_task_3_in_a_row.ui.screens.main.domain.generateLevels

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val levels = remember { generateLevels() }

    val passedLevels = mapOf(1 to 3, 2 to 3, 3 to 2, 4 to 1)
    val currentMoney = 1250

    NavHost(navController = navController, startDestination = Screen.Main.route) {

        composable(Screen.Main.route) {
            MainScreen(
                onPlayClick = { navController.navigate(Screen.Game.createRoute(passedLevels.size)) },
                onLevelsClick = { navController.navigate(Screen.Levels.route)},
                onShopClick = {}
            )
        }

        composable(Screen.Levels.route) {
            LevelsScreen(
                money = currentMoney,
                passedLevels = passedLevels,
                onLevelClick = { levelId ->
                    navController.navigate(Screen.Game.createRoute(levelId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Game.route,
            arguments = listOf(navArgument("levelId") { type = NavType.IntType })
        ) { backStackEntry ->
            val levelId = backStackEntry.arguments?.getInt("levelId") ?: 1

            val levelConfig = levels.find { it.id == levelId } ?: levels.first()

            GameScreen(
                levelNumber = levelConfig.id,
                money = currentMoney,
                moves = levelConfig.moves,
                initialGoals = levelConfig.goals,
                navController = navController
            )
        }
    }
}