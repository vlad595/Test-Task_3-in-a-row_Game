package com.example.test_task_3_in_a_row.data.models

import androidx.compose.ui.graphics.Color

enum class ItemState {
    EQUIPPED,
    OWNED,
    NOT_OWNED
}

data class ShopItemTheme(
    val mainColor: Color,
    val leftColor: Color,
    val rightColor: Color
)

data class ShopItem(
    val id: String,
    val title: String,
    val price: Int,
    val state: ItemState,
    val theme: ShopItemTheme
)