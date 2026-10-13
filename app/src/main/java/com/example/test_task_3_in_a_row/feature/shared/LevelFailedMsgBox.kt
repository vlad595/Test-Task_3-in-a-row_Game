package com.example.test_task_3_in_a_row.feature.shared

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
fun LevelFailedOverlay(
    onRetryClick: () -> Unit,
    onLevelsClick: () -> Unit,
    onHomeClick: () -> Unit,
    onDismiss: () -> Unit,
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
            LevelFailedCard(
                score = score.toString(),
                onRetryClick = onRetryClick,
                onLevelsClick = onLevelsClick,
                onHomeClick = onHomeClick
            )
        }
    }
}

@Composable
fun LevelFailedCard(
    score: String,
    onRetryClick: () -> Unit,
    onLevelsClick: () -> Unit,
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

            // Порожні зірки (символізують програш)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StarIcon(
                    isLit = false,
                    modifier = Modifier.size(45.dp).padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                StarIcon(
                    isLit = false,
                    modifier = Modifier.size(65.dp).padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                StarIcon(
                    isLit = false,
                    modifier = Modifier.size(45.dp).padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SCORE (Рахунок, який вдалося набрати)
            Text(
                text = "SCORE",
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

            Spacer(modifier = Modifier.height(32.dp))

            // Головна кнопка RETRY
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
                    .clickable { onRetryClick() }
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
                    text = "RETRY",
                    color = GameColors.PrimaryButtonText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Нижні кнопки: LEVELS та HOME
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SmallGameButton(
                    text = "LEVELS",
                    onClick = onLevelsClick,
                    modifier = Modifier.weight(1f)
                )
                SmallGameButton(
                    text = "HOME",
                    onClick = onHomeClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Плашка "LEVEL FAILED" зверху
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
                        // Можна зробити градієнт темнішим/сірішим, якщо потрібно,
                        // але поточні червоні кольори також чудово підходять для "Failed"
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
                    text = "FAILED", // Змінено на FAILED
                    color = GameColors.HeaderTitleText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LevelFailedOverlayPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        LevelFailedOverlay(
            onRetryClick = {},
            onLevelsClick = {},
            onHomeClick = {},
            onDismiss = {},
            score = 840
        )
    }
}