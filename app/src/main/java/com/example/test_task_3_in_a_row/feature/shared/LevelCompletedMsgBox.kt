package com.example.test_task_3_in_a_row.feature.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LevelCompleteOverlay(
    onNextLevelClick: () -> Unit,
    onRetryClick: () -> Unit,
    onHomeClick: () -> Unit,
    onDismiss: () -> Unit,
    coinsEarned: Int,
    score: Int
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f))
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
            LevelCompleteCard(
                score = score.toString(),
                coins = "+$coinsEarned",
                starsCount = 2,
                onNextLevelClick = onNextLevelClick,
                onRetryClick = onRetryClick,
                onHomeClick = onHomeClick
            )
        }
    }
}

@Composable
fun LevelCompleteCard(
    score: String,
    coins: String,
    starsCount: Int,
    onNextLevelClick: () -> Unit,
    onRetryClick: () -> Unit,
    onHomeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(320.dp)
            .padding(top = 24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        // Основний темний контейнер
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    color = Color(0xFF160808),
                    shape = RoundedCornerShape(32.dp)
                )
                .border(
                    width = 1.5.dp,
                    color = Color(0xFF7A362D),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StarIcon(
                    isLit = starsCount >= 1,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                StarIcon(
                    isLit = starsCount >= 2,
                    modifier = Modifier
                        .size(65.dp)
                        .padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                StarIcon(
                    isLit = starsCount >= 3,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "FINAL SCORE",
                color = Color(0xFFA57D71),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Text(
                text = score,
                color = Color(0xFFFFF2EB),
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

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
                    Text(
                        text = "$",
                        color = Color(0xFF5A3E00),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = coins,
                    color = Color(0xFFF0B33A),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = Color(0xFFD34F30),
                        spotColor = Color(0xFFD34F30)
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFB53F2E), Color(0xFF5F1E15))
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .clickable { onNextLevelClick() }
                    .padding(vertical = 4.dp), // Для створення 3D-бордюру
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(14.dp)
                        .align(Alignment.TopCenter)
                        .background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(50.dp))
                )

                Text(
                    text = "NEXT LEVEL",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SmallGameButton(
                    text = "RETRY",
                    onClick = onRetryClick,
                    modifier = Modifier.weight(1f)
                )
                SmallGameButton(
                    text = "HOME",
                    onClick = onHomeClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Box(
            modifier = Modifier
                .offset(y = (-16).dp)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = false,
                    ambientColor = Color(0xFFB53F2E),
                    spotColor = Color(0xFFB53F2E)
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFB33227), Color(0xFF6C1914))
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .border(2.dp, Color(0xFFD54F3F), RoundedCornerShape(16.dp))
                .padding(horizontal = 40.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "LEVEL",
                    color = Color(0xFFFF9C8A),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "COMPLETE",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
fun StarIcon(isLit: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (isLit) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .blur(16.dp)
                    .background(Color(0xFFFFD43F).copy(alpha = 0.5f), RoundedCornerShape(50.dp))
            )
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Lit Star",
                tint = Color(0xFFFFD13B),
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Unlit Star",
                tint = Color(0xFF331613),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun SmallGameButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF421C16), Color(0xFF200C09))
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .border(1.dp, Color(0xFF5D2821), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(8.dp)
                .align(Alignment.TopCenter)
                .background(Color.White.copy(alpha = 0.08f), RoundedCornerShape(50.dp))
        )

        Text(
            text = text,
            color = Color(0xFFE6C0B4),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LevelCompleteOverlayPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        LevelCompleteOverlay(
            onNextLevelClick = {},
            onRetryClick = {},
            onHomeClick = {},
            onDismiss = {},
            243,
            1256
        )
    }
}