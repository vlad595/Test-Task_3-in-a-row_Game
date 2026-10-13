package com.example.test_task_3_in_a_row.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_task_3_in_a_row.ui.screens.home.GameBlock
import com.example.test_task_3_in_a_row.ui.theme.GameColors

@Composable
fun PauseOverlay(
    onResumeClick: () -> Unit,
    onRestartClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onHomeClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GameColors.OverlayBackground)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {}
        ) {
            PauseCard(
                onResumeClick = onResumeClick,
                onRestartClick = onRestartClick,
                onSettingsClick = onSettingsClick,
                onHomeClick = onHomeClick
            )
        }
    }
}

@Composable
fun PauseCard(
    onResumeClick: () -> Unit,
    onRestartClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onHomeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(320.dp)
            .padding(top = 28.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    color = GameColors.CardBackground,
                    shape = RoundedCornerShape(32.dp)
                )
                .border(
                    width = 1.5.dp,
                    color = GameColors.CardBorder,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "PAUSED",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GameBlock(
                    color = Color(0xFF4699C8),
                    icon = Icons.Default.KeyboardArrowUp,
                    size = 48
                )
                GameBlock(
                    color = Color(0xFF62B056),
                    icon = Icons.Default.RadioButtonUnchecked,
                    size = 48
                )
                GameBlock(
                    color = Color(0xFFE2B441),
                    icon = Icons.Default.Star,
                    size = 48
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = GameColors.PrimaryButtonShadow,
                        spotColor = GameColors.PrimaryButtonShadow
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(GameColors.PrimaryButtonGradientStart, GameColors.PrimaryButtonGradientEnd)
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .clickable { onResumeClick() }
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(14.dp)
                        .align(Alignment.TopCenter)
                        .background(GameColors.PrimaryButtonHighlight, RoundedCornerShape(50.dp))
                )

                Text(
                    text = "RESUME",
                    color = GameColors.PrimaryButtonText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            WideSecondaryButton(text = "RESTART", onClick = onRestartClick)
            Spacer(modifier = Modifier.height(12.dp))
            WideSecondaryButton(text = "SETTINGS", onClick = onSettingsClick)
            Spacer(modifier = Modifier.height(12.dp))
            WideSecondaryButton(text = "HOME", onClick = onHomeClick)
        }

        Box(
            modifier = Modifier
                .offset(y = (-28).dp)
                .size(56.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = CircleShape,
                    clip = false,
                    ambientColor = GameColors.HeaderShadow,
                    spotColor = GameColors.HeaderShadow
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(GameColors.HeaderGradientStart, GameColors.HeaderGradientEnd)
                    ),
                    shape = CircleShape
                )
                .border(2.dp, GameColors.HeaderBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Pause,
                contentDescription = "Pause Icon",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun WideSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GameColors.SecondaryButtonGradientStart, GameColors.SecondaryButtonGradientEnd)
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .border(1.dp, GameColors.SecondaryButtonBorder, RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(12.dp)
                .align(Alignment.TopCenter)
                .background(GameColors.SecondaryButtonHighlight, RoundedCornerShape(50.dp))
        )

        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PauseOverlayPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        PauseOverlay(
            onResumeClick = {},
            onRestartClick = {},
            onSettingsClick = {},
            onHomeClick = {},
            onDismiss = {}
        )
    }
}