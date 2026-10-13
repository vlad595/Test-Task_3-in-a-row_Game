package com.example.test_task_3_in_a_row.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.test_task_3_in_a_row.data.models.Screen
import com.example.test_task_3_in_a_row.data.models.generateLevels
import com.example.test_task_3_in_a_row.ui.screens.home.MainScreen
import com.example.test_task_3_in_a_row.ui.screens.levels.LevelsScreen
import com.example.test_task_3_in_a_row.ui.screens.main.GameScreen
import com.example.test_task_3_in_a_row.ui.screens.profile.ProfileScreen
import com.example.test_task_3_in_a_row.ui.screens.settings.SettingsScreen
import com.example.test_task_3_in_a_row.ui.screens.shop.ShopScreen
import com.example.test_task_3_in_a_row.viewModels.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.test_task_3_in_a_row.ui.screens.webview.WebViewScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val levels = remember { generateLevels() }

    NavHost(navController = navController, startDestination = Screen.Main.route) {

        composable(Screen.Main.route) {
            val viewModel: LevelsViewModel = hiltViewModel()
            val coins by viewModel.coins.collectAsState()
            val completedLevels by viewModel.completedLevels.collectAsState()
            val nextLevel = (completedLevels + 1).coerceIn(1, levels.size)

            MainScreen(
                money = coins,
                onPlayClick = { navController.navigate(Screen.Game.createRoute(nextLevel)) },
                onLevelsClick = { navController.navigate(Screen.Levels.route)},
                onShopClick = { navController.navigate(Screen.Shop.route) },
                onStarButtonClick = { navController.navigate(Screen.WebView.route) },
                onSunButtonClick = { navController.navigate(Screen.Profile.route) }
            )
        }

        composable(Screen.Levels.route) {
            val viewModel: LevelsViewModel = hiltViewModel()
            val coins by viewModel.coins.collectAsState()
            val completedLevels by viewModel.completedLevels.collectAsState()

            val passedLevelsMap = (1..completedLevels).associateWith { 3 }

            LevelsScreen(
                money = coins,
                passedLevels = passedLevelsMap,
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

            val viewModel: GameViewModel = hiltViewModel()
            val coins by viewModel.coins.collectAsState()

            GameScreen(
                levelNumber = levelConfig.id,
                money = coins,
                moves = levelConfig.moves,
                initialGoals = levelConfig.goals,
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(route = Screen.Shop.route) {
            val viewModel: ShopViewModel = hiltViewModel()
            ShopScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Settings.route) {
            val viewModel: SettingsViewModel = hiltViewModel()
            SettingsScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Profile.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = Screen.WebView.route){
            WebViewScreen("https://lk.nsq.market/ru/tools/testing", onBackToApp = {navController.popBackStack()})
        }
    }
}
