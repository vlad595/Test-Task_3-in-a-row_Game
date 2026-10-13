package com.example.test_task_3_in_a_row.viewModels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChangeHistory
import androidx.compose.material.icons.filled.Hexagon
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_task_3_in_a_row.data.models.ItemState
import com.example.test_task_3_in_a_row.data.models.ShopItem
import com.example.test_task_3_in_a_row.data.models.ShopItemTheme
import com.example.test_task_3_in_a_row.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {

    val coins: StateFlow<Int> = repository.coinsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 1250)

    private val baseItems = listOf(
        ShopItem("1", "PAINITE", 0, ItemState.NOT_OWNED, ShopItemTheme(
            Color(0xFFD64E4E), Icons.Default.Star,
            Color(0xFF4699C8), Icons.Default.ChangeHistory,
            Color(0xFF62B056), Icons.Default.RadioButtonUnchecked
        )),
        ShopItem("2", "GEAR", 0, ItemState.NOT_OWNED, ShopItemTheme(
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

    val items: StateFlow<List<ShopItem>> = combine(
        repository.ownedShopItemsFlow,
        repository.equippedShopItemFlow
    ) { ownedIds, equippedId ->
        baseItems.map { item ->
            val newState = when {
                item.id == equippedId -> ItemState.EQUIPPED
                ownedIds.contains(item.id) -> ItemState.OWNED
                else -> ItemState.NOT_OWNED
            }
            item.copy(state = newState)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), baseItems)

    fun onShopItemClicked(item: ShopItem) {
        viewModelScope.launch {
            when (item.state) {
                ItemState.NOT_OWNED -> {
                    if (repository.spendCoins(item.price)) {
                        repository.buyShopItem(item.id)
                        repository.equipShopItem(item.id)
                    }
                }
                ItemState.OWNED -> {
                    repository.equipShopItem(item.id)
                }
                ItemState.EQUIPPED -> {}
            }
        }
    }
}