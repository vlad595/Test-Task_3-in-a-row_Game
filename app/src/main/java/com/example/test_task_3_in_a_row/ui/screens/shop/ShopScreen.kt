package com.example.test_task_3_in_a_row.ui.screens.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ShopScreen(
    viewModel: ShopViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val items by viewModel.items.collectAsState()
    val coins by viewModel.coins.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF160808)) // Темний фон екрану
            .padding(top = 48.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Шапка (Кнопка назад + Баланс)
        ShopHeader(coins = coins, onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(24.dp))

        // Заголовок SHOP
        Text(
            text = "SHOP",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .width(40.dp)
                .height(4.dp)
                .background(Color(0xFFC94236), RoundedCornerShape(50.dp))
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Сітка предметів
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(items) { item ->
                ShopItemCard(
                    item = item,
                    onClick = { viewModel.onShopItemClicked(item) }
                )
            }
        }
    }
}

@Composable
fun ShopHeader(coins: Int, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Кнопка назад
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFF220E0A), RoundedCornerShape(12.dp))
                .clickable { onBackClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        // Баланс монет
        Row(
            modifier = Modifier
                .background(Color(0xFF220E0A), RoundedCornerShape(50.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFFF1A1), Color(0xFFDCA825))
                        ),
                        shape = RoundedCornerShape(50.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "$", color = Color(0xFF5A3E00), fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "%,d".format(coins),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ShopItemCard(item: ShopItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF160808), RoundedCornerShape(20.dp))
            .border(1.5.dp, Color(0xFF4A1F1B), RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Композиція кубиків
        ShopItemCluster(theme = item.theme)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = item.title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Відображення статусу (Equipped, Owned, Price)
        when (item.state) {
            ItemState.EQUIPPED -> StatusBadge(text = "EQUIPPED", isGreen = true)
            ItemState.OWNED -> StatusBadge(text = "OWNED", isGreen = true)
            ItemState.NOT_OWNED -> PriceBadge(price = item.price)
        }
    }
}

// Імітація 3-х кубиків з дизайну
@Composable
fun ShopItemCluster(theme: ShopItemTheme) {
    Box(modifier = Modifier.size(80.dp), contentAlignment = Alignment.Center) {
        // Лівий нижній кубик
        Box(
            modifier = Modifier
                .offset(x = (-24).dp, y = 12.dp)
                .size(32.dp)
                .background(theme.leftColor, RoundedCornerShape(8.dp))
        )
        // Правий нижній кубик
        Box(
            modifier = Modifier
                .offset(x = 24.dp, y = 12.dp)
                .size(32.dp)
                .background(theme.rightColor, RoundedCornerShape(8.dp))
        )
        // Головний центральний кубик
        Box(
            modifier = Modifier
                .offset(y = (-10).dp)
                .size(48.dp)
                .background(theme.mainColor, RoundedCornerShape(12.dp))
        )
    }
}

@Composable
fun StatusBadge(text: String, isGreen: Boolean) {
    Box(
        modifier = Modifier
            .background(
                color = if (isGreen) Color(0xFF5A9E66) else Color(0xFF220E0A),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 16.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PriceBadge(price: Int) {
    Row(
        modifier = Modifier
            .background(Color(0xFF220E0A), RoundedCornerShape(50.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(
                    brush = Brush.verticalGradient(listOf(Color(0xFFFFF1A1), Color(0xFFDCA825))),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "$", color = Color(0xFF5A3E00), fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = price.toString(),
            color = Color(0xFFF0B33A),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}