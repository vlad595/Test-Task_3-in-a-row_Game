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
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.runtime.key
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.test_task_3_in_a_row.core.navigation.Screen
import com.example.test_task_3_in_a_row.feature.shared.LevelCompleteOverlay

@Composable
fun GameScreen(
    levelNumber: Int,
    money: Int,
    moves: Int,
    initialGoals: List<LevelGoal>,
    viewModel: GameViewModel = viewModel(),
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        viewModel.initLevel(initialGoals, moves)
    }
    val board by viewModel.board.collectAsState()
    val hintCells by viewModel.hintCells.collectAsState()

    val currentGoals by viewModel.goals.collectAsState()

    var selectedCell by remember { mutableStateOf<Cell?>(null) }

    val _moves by viewModel.moves.collectAsState()

    val score by viewModel.score.collectAsState()

    val levelPassedSignal by viewModel.levelPassedSignal.collectAsState()
    val levelFailedSignal by viewModel.levelFailedSignal.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF140908))
            .padding(top = 48.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(money = money, levelNumber = levelNumber + 1, onBackClick = {})
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoPanel(title = "SCORE", value = score.toString(), modifier = Modifier.weight(1f))
            InfoPanel(title = "MOVES", value = _moves.toString(), modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(32.dp))

        GameBoard(
            board = board,
            selectedCell = selectedCell,
            hintCells = hintCells,
            onCellClick = { clickedCell ->
                if (selectedCell == null) {
                    selectedCell = clickedCell
                } else {
                    if (selectedCell != clickedCell) {
                        viewModel.swapCubes(selectedCell!!, clickedCell)
                    }
                    selectedCell = null
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))
        GoalPanel(goals = currentGoals)
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            ActionButton(
                text = "HINT", color = Color(0xFFA12921), modifier = Modifier.weight(1f),
                onClick = { viewModel.hint() }
            )
            ActionButton(
                text = "SHUFFLE", color = Color(0xFF2A1513), modifier = Modifier.weight(1f),
                onClick = { viewModel.shuffleBoard() }
            )
        }
    }
    if (levelPassedSignal){
        LevelCompleteOverlay(
            onNextLevelClick = { navController.navigate(Screen.Game.createRoute(levelNumber + 1)) },
            onRetryClick = { navController.navigate(Screen.Game.createRoute(levelNumber)) },
            onDismiss = {},
            onHomeClick = { navController.navigate((Screen.Main.route)) },
            coindEarned = score / 10,
            score = score
        )
    }
    if (levelFailedSignal){

    }
}

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

@Composable
fun GameBoard(
    board: List<List<Cell>>,
    selectedCell: Cell?,
    hintCells: Pair<Cell, Cell>?,
    onCellClick: (Cell) -> Unit
) {
    if (board.isEmpty() || board.first().isEmpty()) return

    val rows = board.size
    val cols = board.first().size

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(cols.toFloat() / rows.toFloat())
            .background(Color(0xFF21100E), RoundedCornerShape(16.dp))
            .padding(4.dp).clipToBounds()
    ) {
        val cellWidth = maxWidth / cols
        val cellHeight = maxHeight / rows

        board.flatten().forEach { cell ->
            val isSelected = cell.id == selectedCell?.id
            val isHinted = cell.id == hintCells?.first?.id || cell.id == hintCells?.second?.id

            key(cell.id) {
                AnimatedCube(
                    cell = cell,
                    cellWidth = cellWidth,
                    cellHeight = cellHeight,
                    isSelected = isSelected,
                    isHinted = isHinted,
                    onClick = { onCellClick(cell) }
                )
            }
        }
    }
}

@Composable
fun AnimatedCube(
    cell: Cell,
    cellWidth: androidx.compose.ui.unit.Dp,
    cellHeight: androidx.compose.ui.unit.Dp,
    isSelected: Boolean,
    isHinted: Boolean,
    onClick: () -> Unit
) {
    var isSpawned by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isSpawned = true
    }

    val targetOffsetX = cellWidth * cell.col
    val targetOffsetY = cellHeight * cell.row

    val startOffsetY = targetOffsetY - (cellHeight * 6)

    val offsetX by animateDpAsState(
        targetValue = targetOffsetX,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "x"
    )

    val offsetY by animateDpAsState(
        targetValue = if (isSpawned) targetOffsetY else startOffsetY,

        animationSpec = spring(
            dampingRatio = 0.65f,
            stiffness = Spring.StiffnessLow
        ),
        label = "y"
    )

    val scale by animateFloatAsState(
        targetValue = when {
            isSelected -> 1.15f
            isHinted -> 1.05f
            else -> 1f
        },
        animationSpec = tween(150),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .offset(x = offsetX, y = offsetY)
            .size(width = cellWidth, height = cellHeight)
            .padding(2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .background(cell.type.color)
                .clickable { onClick() }
                .border(
                    width = if (isSelected || isHinted) 3.dp else 0.dp,
                    color = if (isSelected) Color.White else if (isHinted) Color.Yellow else Color.Transparent,
                    shape = RoundedCornerShape(12.dp)
                )
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
            contentAlignment = Alignment.Center
        ) {
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