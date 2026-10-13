package com.example.test_task_3_in_a_row.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_task_3_in_a_row.ui.shared.Header

val BgColor = Color(0xFF140908)
val RedBlock = Color(0xFFD34A42)
val BlueBlock = Color(0xFF4A90E2)
val GreenBlock = Color(0xFF67B75B)
val YellowBlock = Color(0xFFE4BC4B)
val PurpleBlock = Color(0xFF9B63E7)

@Composable
fun MainScreen(onPlayClick: () -> Unit, onLevelsClick: () -> Unit, onShopClick: () -> Unit, onSunButtonClick: () -> Unit, onStarButtonClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(top = 48.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(1250, isMainScreen = true, onBackClick = { }, onSunButtonClick = onSunButtonClick, onStarButtonClick = onStarButtonClick)

        Spacer(modifier = Modifier.weight(0.8f))

        HeroLogoGroup()

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "PAINITE",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )
        Text(
            text = "GEAR",
            color = Color(0xFFD34A42),
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .background(Color(0xFF261210), RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = "SWAP • MATCH",
                color = Color.LightGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GameBlock(color = RedBlock, icon = Icons.Default.Star)
            GameBlock(color = BlueBlock, icon = Icons.Default.ChangeHistory) // Трикутник
            GameBlock(color = GreenBlock, icon = Icons.Default.RadioButtonUnchecked) // Коло
            GameBlock(color = YellowBlock, icon = Icons.Default.Hexagon)
            GameBlock(color = PurpleBlock, icon = Icons.Default.Add)
        }

        Spacer(modifier = Modifier.weight(1f))

        MenuButton(onClick = { onPlayClick() }, text = "PLAY", color = Color(0xFFA12921), height = 72.dp, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(12.dp))
        MenuButton(onClick = onLevelsClick, text = "LEVELS", color = Color(0xFF2A1513))
        Spacer(modifier = Modifier.height(12.dp))
        MenuButton(onClick = onShopClick, text = "SHOP", color = Color(0xFF2A1513))

        Spacer(modifier = Modifier.height(24.dp))

        DailyRewardButton()
    }
}

@Composable
fun GameBlock(color: Color, icon: ImageVector, size: Int = 56) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .background(color, RoundedCornerShape(14.dp))
            .border(2.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size((size * 0.5).dp)
        )
    }
}

@Composable
fun HeroLogoGroup() {
    Box(
        modifier = Modifier
            .size(140.dp)
            .background(Color.White.copy(alpha = 0.05f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 20.dp, x = (-15).dp)) {
                GameBlock(color = RedBlock, icon = Icons.Default.Star, size = 64)
            }
            Box(modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = 30.dp, x = (-20).dp)) {
                GameBlock(color = GreenBlock, icon = Icons.Default.RadioButtonUnchecked, size = 52)
            }
            Box(modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = (-30).dp, x = 30.dp)) {
                GameBlock(color = YellowBlock, icon = Icons.Default.Hexagon, size = 48)
            }
            Box(modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = (-20).dp, x = (-40).dp)) {
                GameBlock(color = PurpleBlock, icon = Icons.Default.Add, size = 48)
            }
        }
    }
}

@Composable
fun MenuButton(
    text: String,
    color: Color,
    height: Dp = 56.dp,
    fontSize: TextUnit = 18.sp,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .height(height)
            .clip(RoundedCornerShape(32.dp))
            .background(color)
            // Імітація внутрішнього світіння/об'єму
            .border(3.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(32.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = fontSize,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun DailyRewardButton() {
    Row(
        modifier = Modifier
            .background(Color(0xFF21100E), RoundedCornerShape(24.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        Text(
            text = "DAILY REWARD",
            color = Color.LightGray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(Color(0xFFE4BC4B), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen(onPlayClick = {}, onShopClick = {}, onLevelsClick = {}, onStarButtonClick = {}, onSunButtonClick = {})
}