package com.example.test_task_3_in_a_row.ui.screens.levels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_task_3_in_a_row.ui.shared.Header

// Кольори з дизайну
val BgColor = Color(0xFF140908)
val GreenLevel = Color(0xFF538C52)
val GreenHighlight = Color(0xFF6B9C69)
val RedLevel = Color(0xFF8B332F)
val RedHighlight = Color(0xFF9E4B46)
val LockedLevel = Color(0xFF21100E)
val LockedHighlight = Color(0xFF2E1714)
val StarGold = Color(0xFFE4BC4B)
val StarEmptyGreen = Color(0xFF406B3E)
val StarEmptyRed = Color(0xFF632421)

enum class LevelStatus { PASSED, NEXT, LOCKED }

@Composable
fun LevelsScreen(
    onLevelClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    money: Int,
    passedLevels: Map<Int, Int>
) {
    val nextLevelNumber = (passedLevels.keys.maxOrNull() ?: 0) + 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(top = 48.dp)
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            Header(money, onBackClick = onBackClick)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "LEVELS",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(4.dp)
                    .background(Color(0xFFD34A42), RoundedCornerShape(2.dp))
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(start = 32.dp, end = 32.dp, bottom = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(100) { index ->
                val levelNum = index + 1
                val stars = passedLevels[levelNum] ?: 0

                val status = when {
                    passedLevels.containsKey(levelNum) -> LevelStatus.PASSED
                    levelNum == nextLevelNumber -> LevelStatus.NEXT
                    else -> LevelStatus.LOCKED
                }

                LevelItem(
                    levelNum = levelNum,
                    status = status,
                    stars = stars,
                    onClick = {
                        onLevelClick(index)
                    }
                )
            }
        }
    }
}

@Composable
fun LevelItem(
    levelNum: Int,
    status: LevelStatus,
    stars: Int,
    onClick: () -> Unit
) {
    // Налаштування кольорів залежно від статусу рівня
    val (bgColor, highlightColor, starEmptyColor) = when (status) {
        LevelStatus.PASSED -> Triple(GreenLevel, GreenHighlight, StarEmptyGreen)
        LevelStatus.NEXT -> Triple(RedLevel, RedHighlight, StarEmptyRed)
        LevelStatus.LOCKED -> Triple(LockedLevel, LockedHighlight, Color.Transparent)
    }

    val isClickable = status != LevelStatus.LOCKED

    Box(
        modifier = Modifier
            .aspectRatio(1f) // Робимо кнопку квадратною
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .clickable(enabled = isClickable, onClick = onClick)
    ) {
        // Світлий "відблиск" у верхній частині кнопки
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(6.dp)
                .background(highlightColor, RoundedCornerShape(10.dp))
        )

        if (status == LevelStatus.LOCKED) {
            // Для заблокованих показуємо тільки іконку замка[cite: 3]
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Locked",
                tint = Color(0xFF3B1C1A),
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.Center)
            )
        } else {
            // Для відкритих показуємо номер та зірки[cite: 3]
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = levelNum.toString(),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    // Малюємо 3 зірки
                    repeat(3) { starIndex ->
                        val isEarned = starIndex < stars
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (isEarned) StarGold else starEmptyColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

// Прев'ю екрану з тестовими даними
@Preview(showSystemUi = true)
@Composable
fun LevelsScreenPreview() {
    // Імітуємо збережений прогрес гравця
    val testPassedLevels = mapOf(
        1 to 3, // 1 рівень - 3 зірки
        2 to 3, // 2 рівень - 3 зірки
        3 to 3, // 3 рівень - 3 зірки
        4 to 1, // 4 рівень - 3 зірки
        5 to 2  // 5 рівень - 3 зірки
        // Наступний доступний буде 6-й
    )

    LevelsScreen(
        money = 1250,
        passedLevels = testPassedLevels,
        onLevelClick = {},
        onBackClick = {}
    )
}