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
import com.example.test_task_3_in_a_row.ui.theme.GameColors

@Composable
fun LevelCompleteOverlay(
    onNextLevelClick: () -> Unit,
    onRetryClick: () -> Unit,
    onHomeClick: () -> Unit,
    onDismiss: () -> Unit,
    coindEarned: Int,
    score: Int
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
            LevelCompleteCard(
                score = score.toString(),
                coins = "+$coindEarned",
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
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StarIcon(
                    isLit = starsCount >= 1,
                    modifier = Modifier.size(45.dp).padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                StarIcon(
                    isLit = starsCount >= 2,
                    modifier = Modifier.size(65.dp).padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                StarIcon(
                    isLit = starsCount >= 3,
                    modifier = Modifier.size(45.dp).padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "FINAL SCORE",
                color = GameColors.ScoreLabelText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Text(
                text = score,
                color = GameColors.ScoreValueText,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .background(GameColors.CoinsContainerBackground, RoundedCornerShape(50.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(GameColors.CoinIconGradientStart, GameColors.CoinIconGradientEnd)
                            ),
                            shape = RoundedCornerShape(50.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$",
                        color = GameColors.CoinSymbolText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = coins,
                    color = GameColors.CoinsValueText,
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
                        ambientColor = GameColors.PrimaryButtonShadow,
                        spotColor = GameColors.PrimaryButtonShadow
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(GameColors.PrimaryButtonGradientStart, GameColors.PrimaryButtonGradientEnd)
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .clickable { onNextLevelClick() }
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
                    text = "NEXT LEVEL",
                    color = GameColors.PrimaryButtonText,
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
                    ambientColor = GameColors.HeaderShadow,
                    spotColor = GameColors.HeaderShadow
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(GameColors.HeaderGradientStart, GameColors.HeaderGradientEnd)
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .border(2.dp, GameColors.HeaderBorder, RoundedCornerShape(16.dp))
                .padding(horizontal = 40.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "LEVEL",
                    color = GameColors.HeaderLabelText,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "COMPLETE",
                    color = GameColors.HeaderTitleText,
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
                    .background(GameColors.StarGlow, RoundedCornerShape(50.dp))
            )
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Lit Star",
                tint = GameColors.StarLit,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Unlit Star",
                tint = GameColors.StarUnlit,
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
                    colors = listOf(GameColors.SecondaryButtonGradientStart, GameColors.SecondaryButtonGradientEnd)
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .border(1.dp, GameColors.SecondaryButtonBorder, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(8.dp)
                .align(Alignment.TopCenter)
                .background(GameColors.SecondaryButtonHighlight, RoundedCornerShape(50.dp))
        )

        Text(
            text = text,
            color = GameColors.SecondaryButtonText,
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
            coindEarned = 245,
            score = 1265
        )
    }
}