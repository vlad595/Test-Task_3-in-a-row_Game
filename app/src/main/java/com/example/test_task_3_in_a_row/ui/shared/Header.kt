package com.example.test_task_3_in_a_row.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star

@Composable
fun Header(money: Int, levelNumber: Int = 0, isMainScreen: Boolean = false, modifier: Modifier = Modifier, onBackClick: () -> Unit) {

    if (isMainScreen) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MoneyLabel(money)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomIconButton(icon = Icons.Default.Star, onClick = {})
                CustomIconButton(icon = Icons.Default.Settings, onClick = {})
            }
        }

    } else if (levelNumber > 0) {
        Box(
            modifier = modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.align(Alignment.CenterStart)) {
                CustomIconButton(icon = Icons.Default.Pause, onClick = onBackClick)
            }

            LevelLabel(
                levelNumber = levelNumber,
                modifier = Modifier.align(Alignment.Center)
            )

            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                MoneyLabel(money)
            }
        }

    } else {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomIconButton(icon = Icons.Default.ArrowBackIosNew, onClick = {onBackClick()})
            MoneyLabel(money)
        }
    }
}

@Composable
fun MoneyLabel(money: Int){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(color = Color(0xFF210C0A), shape = RoundedCornerShape(28.dp)).padding(4.dp)
    ) {
        Box(modifier = Modifier.size(32.dp).background(color = Color(0xFFE0A024), shape = RoundedCornerShape(26.dp))){
            Text(
               text = "$",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 20.sp
            )
        }
        Text(
            text = money.toString(),
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 22.dp)
        )
    }
}

@Composable
fun LevelLabel(levelNumber: Int, modifier: Modifier = Modifier) {
    Text(
        text = "Level $levelNumber",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = modifier
    )
}

@Composable
fun CustomIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription: String? = null
) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(36.dp),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF210C0A)
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun HomeHeaderPreview(){
    Header(1020, modifier = Modifier.padding(16.dp), onBackClick = {})
}

@Composable
@Preview()
fun GameHeaderPreview(){
    Header(1020, 6, onBackClick = {})
}

@Composable
@Preview()
fun DefaultHeaderPreview(){
    Header(1020, isMainScreen = true, onBackClick = {})
}