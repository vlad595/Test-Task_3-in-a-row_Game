package com.example.test_task_3_in_a_row.feature.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test_task_3_in_a_row.feature.main.presentation.domain.Cell
import com.example.test_task_3_in_a_row.feature.main.presentation.domain.LevelGoal
import com.example.test_task_3_in_a_row.feature.shared.Header

@Composable
fun GameScreen(
    onBackClick: () -> Unit,
    levelNumber: Int,
    money: Int,
    moves: Int,
    goals: List<LevelGoal>,
    viewModel: GameViewModel = viewModel()
) {
    val board by viewModel.board.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF140908)) // BgColor
            .padding(top = 48.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Header (Пауза, Рівень, Гроші)[cite: 4]
        Header(money = money, levelNumber = levelNumber, onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Score & Moves Panel[cite: 4]
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoPanel(title = "SCORE", value = "12,480", modifier = Modifier.weight(1f))
            InfoPanel(title = "MOVES", value = moves.toString(), modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 3. Ігрова дошка 6x6[cite: 4]
        GameBoard(board = board, onCellClick = { cell ->
            // Тут потрібно обробляти вибір двох клітинок для swap
        })

        Spacer(modifier = Modifier.weight(1f))

        // 4. Панель цілей (Goal)[cite: 4]
        GoalPanel(goals = goals)

        Spacer(modifier = Modifier.height(24.dp))

        // 5. Кнопки HINT та SHUFFLE[cite: 4]
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            ActionButton(
                text = "HINT",
                color = Color(0xFFA12921),
                modifier = Modifier.weight(1f),
                onClick = { viewModel.hint() }
            )
            ActionButton(
                text = "SHUFFLE",
                color = Color(0xFF2A1513),
                modifier = Modifier.weight(1f),
                onClick = { viewModel.shuffleBoard() }
            )
        }
    }
}

// Допоміжний компонент для SCORE та MOVES[cite: 4]
@Composable
fun InfoPanel(title: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color(0xFF21100E), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(text = title, color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Text(text = value, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black)
    }
}

// Компонент ігрової дошки[cite: 4]
@Composable
fun GameBoard(board: List<List<Cell>>, onCellClick: (Cell) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (row in board) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (cell in row) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(cell.type.color)
                            .clickable { onCellClick(cell) },
                        contentAlignment = Alignment.Center
                    ) {
                        // Відблиск (світла смужка зверху)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp)
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .align(Alignment.TopCenter)
                        )
                        Icon(
                            imageVector = cell.type.icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

// Панель цілей (Goal)[cite: 4]
@Composable
fun GoalPanel(goals: List<LevelGoal>) {
    Row(
        modifier = Modifier
            .background(Color(0xFF21100E), RoundedCornerShape(24.dp))
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("GOAL", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            goals.forEach { goal ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Маленький кубик мети[cite: 4]
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(goal.type.color, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = goal.type.icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text("x${goal.count}", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// Кнопки дій (HINT / SHUFFLE)[cite: 4]
@Composable
fun ActionButton(text: String, color: Color, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, fontWeight = FontWeight.Black)
    }
}