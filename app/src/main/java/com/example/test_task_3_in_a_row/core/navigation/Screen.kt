package com.example.test_task_3_in_a_row.core.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object Levels : Screen("levels_screen")
    object Game : Screen("game_screen/{levelId}") {
        fun createRoute(levelId: Int) = "game_screen/$levelId"
    }
}