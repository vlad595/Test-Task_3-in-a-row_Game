package com.example.test_task_3_in_a_row.data.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class ItemState {
    EQUIPPED,
    OWNED,
    NOT_OWNED
}

data class ShopItemTheme(
    val mainColor: Color,
    val mainIcon: ImageVector,
    val leftColor: Color,
    val leftIcon: ImageVector,
    val rightColor: Color,
    val rightIcon: ImageVector
)

data class ShopItem(
    val id: String,
    val title: String,
    val price: Int,
    val state: ItemState,
    val theme: ShopItemTheme
)