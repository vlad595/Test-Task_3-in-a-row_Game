package com.example.test_task_3_in_a_row.viewModels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChangeHistory
import androidx.compose.material.icons.filled.Hexagon
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Star
import androidx.lifecycle.ViewModel
import com.example.test_task_3_in_a_row.data.models.ItemState
import com.example.test_task_3_in_a_row.data.models.ShopItem
import com.example.test_task_3_in_a_row.data.models.ShopItemTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.update

class ShopViewModel : ViewModel() {

    private val _coins = MutableStateFlow(1250)
    val coins: StateFlow<Int> = _coins.asStateFlow()

    private val _items = MutableStateFlow(
        listOf(
            ShopItem("1", "PAINITE", 0, ItemState.EQUIPPED, ShopItemTheme(
                Color(0xFFD64E4E), Icons.Default.Star,
                Color(0xFF4699C8), Icons.Default.ChangeHistory,
                Color(0xFF62B056), Icons.Default.RadioButtonUnchecked
            )),
            ShopItem("2", "GEAR", 0, ItemState.OWNED, ShopItemTheme(
                Color(0xFF4699C8), Icons.Default.ChangeHistory,
                Color(0xFF62B056), Icons.Default.RadioButtonUnchecked,
                Color(0xFFE2B441), Icons.Default.Hexagon
            )),
            ShopItem("3", "GEM", 600, ItemState.NOT_OWNED, ShopItemTheme(
                Color(0xFF62B056), Icons.Default.RadioButtonUnchecked,
                Color(0xFFE2B441), Icons.Default.Hexagon,
                Color(0xFFA57CF0), Icons.Default.Add
            )),
            ShopItem("4", "COG", 750, ItemState.NOT_OWNED, ShopItemTheme(
                Color(0xFFE2B441), Icons.Default.Hexagon,
                Color(0xFFA57CF0), Icons.Default.Add,
                Color(0xFFE7658B), Icons.Default.AutoAwesome
            )),
            ShopItem("5", "RED", 900, ItemState.NOT_OWNED, ShopItemTheme(
                Color(0xFFA57CF0), Icons.Default.Add,
                Color(0xFFE7658B), Icons.Default.AutoAwesome,
                Color(0xFFD64E4E), Icons.Default.Star
            )),
            ShopItem("6", "GLOW", 1050, ItemState.NOT_OWNED, ShopItemTheme(
                Color(0xFFE7658B), Icons.Default.AutoAwesome,
                Color(0xFFD64E4E), Icons.Default.Star,
                Color(0xFF4699C8), Icons.Default.ChangeHistory
            ))
        )
    )
    val items: StateFlow<List<ShopItem>> = _items.asStateFlow()

    fun onShopItemClicked(item: ShopItem) {
        when (item.state) {
            ItemState.NOT_OWNED -> {
                if (_coins.value >= item.price) {
                    _coins.value -= item.price
                    equipItem(item.id)
                }
            }
            ItemState.OWNED -> {
                equipItem(item.id)
            }
            ItemState.EQUIPPED -> {  }
        }
    }

    private fun equipItem(targetId: String) {
        _items.update { currentList ->
            currentList.map { currentItem ->
                when {
                    currentItem.id == targetId -> currentItem.copy(state = ItemState.EQUIPPED)
                    currentItem.state == ItemState.EQUIPPED -> currentItem.copy(state = ItemState.OWNED)
                    else -> currentItem
                }
            }
        }
    }
}