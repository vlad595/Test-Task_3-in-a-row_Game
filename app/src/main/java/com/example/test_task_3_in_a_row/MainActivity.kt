package com.example.test_task_3_in_a_row

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.test_task_3_in_a_row.core.navigation.AppNavigation
import com.example.test_task_3_in_a_row.feature.home.presentation.MainScreen
import com.example.test_task_3_in_a_row.ui.theme.TestTask_3InARowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestTask_3InARowTheme {
                AppNavigation()
            }
        }
    }
}