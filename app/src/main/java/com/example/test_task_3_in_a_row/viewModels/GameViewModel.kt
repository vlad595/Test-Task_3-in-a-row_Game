package com.example.test_task_3_in_a_row.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_task_3_in_a_row.data.models.Cell
import com.example.test_task_3_in_a_row.data.models.CubeType
import com.example.test_task_3_in_a_row.data.models.LevelGoal
import com.example.test_task_3_in_a_row.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.max

@HiltViewModel
class GameViewModel @Inject constructor(private val repository: GameRepository) : ViewModel() {
    private val rows = 6
    private val cols = 6

    private val _board = MutableStateFlow<List<List<Cell>>>(emptyList())
    val board: StateFlow<List<List<Cell>>> = _board.asStateFlow()

    private val _hintCells = MutableStateFlow<Pair<Cell, Cell>?>(null)
    val hintCells: StateFlow<Pair<Cell, Cell>?> = _hintCells.asStateFlow()

    private val _goals = MutableStateFlow<List<LevelGoal>>(emptyList())
    val goals: StateFlow<List<LevelGoal>> = _goals.asStateFlow()

    private val _moves = MutableStateFlow<Int>(0)
    val moves: StateFlow<Int> = _moves.asStateFlow()

    private val _score = MutableStateFlow<Int>(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _levelPassedSignal = MutableStateFlow<Boolean>(false)
    val levelPassedSignal = _levelPassedSignal.asStateFlow()

    private val _levelFailedSignal = MutableStateFlow<Boolean>(false)
    val levelFailedSignal = _levelFailedSignal.asStateFlow()

    val coins: StateFlow<Int> = repository.coinsFlow.stateIn(viewModelScope, SharingStarted.Lazily, 0)
    private var currentLevelId: Int = 1

    init {
        generateBoard()
    }

    private fun generateBoard() {
        var newBoard: List<List<Cell>>
        do {
            newBoard = List(rows) { row ->
                List(cols) { col ->
                    Cell(row, col, CubeType.values().random())
                }
            }
        } while (findMatches(newBoard).isNotEmpty())

        _board.value = newBoard
        _hintCells.value = null
    }

    fun shuffleBoard() {
        generateBoard()
    }

    fun hint() {
        val currentBoard = _board.value

        for (r in 0 until rows) {
            for (c in 0 until cols - 1) {
                if (simulatedSwapHasMatches(currentBoard, r, c, r, c + 1)) {
                    _hintCells.value = Pair(currentBoard[r][c], currentBoard[r][c + 1])
                    return
                }
            }
        }

        for (r in 0 until rows - 1) {
            for (c in 0 until cols) {
                if (simulatedSwapHasMatches(currentBoard, r, c, r + 1, c)) {
                    _hintCells.value = Pair(currentBoard[r][c], currentBoard[r + 1][c])
                    return
                }
            }
        }

        shuffleBoard()
    }

    fun initLevel(levelId: Int, levelGoals: List<LevelGoal>, moves: Int) {
        currentLevelId = levelId
        if (_goals.value.isEmpty()) {
            _goals.value = levelGoals
        }

        if (_moves.value == 0){
            _moves.value = moves
        }
    }

    private fun onLevelWon() {
        viewModelScope.launch {
            val earnedScore = _score.value
            val earnedCoins = earnedScore / 10

            repository.updateCoins(earnedCoins)
            repository.updateBestScore(earnedScore)
            repository.completeLevel(currentLevelId)
            repository.unlockAchievement("1")

            _levelPassedSignal.value = true
        }
    }

    fun swapCubes(cell1: Cell, cell2: Cell) {
        if (!checkNeighbours(cell1, cell2)) return

        viewModelScope.launch {
            var currentBoard = _board.value.map { it.toMutableList() }.toMutableList()

            swapInBoard(currentBoard, cell1.row, cell1.col, cell2.row, cell2.col)
            _board.value = currentBoard.map { it.toList() }

            var matches = findMatches(currentBoard)

            if (matches.isEmpty()) {
                delay(300)
                swapInBoard(currentBoard, cell1.row, cell1.col, cell2.row, cell2.col)
                _board.value = currentBoard.map { it.toList() }
            } else {
                _hintCells.value = null
                _moves.value -= 1
                if (_moves.value == 0){
                    _levelFailedSignal.value = true
                }

                var comboMultiplier = 1

                while (matches.isNotEmpty()) {
                    delay(300)

                    val basePointsPerCube = 10
                    val extraLengthBonus = if (matches.size > 3) (matches.size - 3) * 20 else 0

                    val pointsEarned = ((matches.size * basePointsPerCube) + extraLengthBonus) * comboMultiplier
                    _score.value += pointsEarned

                    updateGoals(currentBoard, matches)

                    removeMatchesAndDrop(currentBoard, matches)
                    _board.value = currentBoard.map { it.toList() }

                    matches = findMatches(currentBoard)
                    comboMultiplier++
                }
            }
        }
    }

    private fun updateGoals(board: List<List<Cell>>, matches: Set<Pair<Int, Int>>) {
        val currentGoals = _goals.value.toMutableList()
        var goalsChanged = false

        val destroyedTypes = matches.map { (r, c) -> board[r][c].type }

        for (i in currentGoals.indices) {
            val goal = currentGoals[i]

            val destroyedCount = destroyedTypes.count { it == goal.type }

            if (destroyedCount > 0 && goal.count > 0) {
                val newCount = max(0, goal.count - destroyedCount)
                currentGoals[i] = goal.copy(count = newCount)
                goalsChanged = true
            }
        }

        if (goalsChanged) {
            _goals.value = currentGoals

            var isWin: Boolean = false

            currentGoals.forEach { goal ->
                if (goal.count == 0){
                    isWin = true
                }
                else isWin = false
            }
            if (isWin){
                onLevelWon()
            }
        }
    }

    fun checkNeighbours(cell1: Cell, cell2: Cell): Boolean {
        val rowDiff = abs(cell1.row - cell2.row)
        val colDiff = abs(cell1.col - cell2.col)

        return (rowDiff + colDiff) == 1
    }

    private fun swapInBoard(board: MutableList<MutableList<Cell>>, r1: Int, c1: Int, r2: Int, c2: Int) {
        val temp = board[r1][c1]
        board[r1][c1] = board[r2][c2].copy(row = r1, col = c1)
        board[r2][c2] = temp.copy(row = r2, col = c2)
    }

    private fun simulatedSwapHasMatches(board: List<List<Cell>>, r1: Int, c1: Int, r2: Int, c2: Int): Boolean {
        val tempBoard = board.map { it.toMutableList() }.toMutableList()
        swapInBoard(tempBoard, r1, c1, r2, c2)
        return findMatches(tempBoard).isNotEmpty()
    }

    private fun findMatches(board: List<List<Cell>>): Set<Pair<Int, Int>> {
        val matchedCoords = mutableSetOf<Pair<Int, Int>>()

        for (r in 0 until rows) {
            var matchLength = 1
            for (c in 1 until cols) {
                if (board[r][c].type == board[r][c - 1].type) {
                    matchLength++
                } else {
                    if (matchLength >= 3) {
                        for (i in 0 until matchLength) matchedCoords.add(Pair(r, c - 1 - i))
                    }
                    matchLength = 1
                }
            }
            if (matchLength >= 3) {
                for (i in 0 until matchLength) matchedCoords.add(Pair(r, cols - 1 - i))
            }
        }

        for (c in 0 until cols) {
            var matchLength = 1
            for (r in 1 until rows) {
                if (board[r][c].type == board[r - 1][c].type) {
                    matchLength++
                } else {
                    if (matchLength >= 3) {
                        for (i in 0 until matchLength) matchedCoords.add(Pair(r - 1 - i, c))
                    }
                    matchLength = 1
                }
            }
            if (matchLength >= 3) {
                for (i in 0 until matchLength) matchedCoords.add(Pair(rows - 1 - i, c))
            }
        }

        return matchedCoords
    }

    private fun removeMatchesAndDrop(board: MutableList<MutableList<Cell>>, matches: Set<Pair<Int, Int>>) {
        for (c in 0 until cols) {
            var emptySpaces = 0
            for (r in rows - 1 downTo 0) {
                if (matches.contains(Pair(r, c))) {
                    emptySpaces++
                } else if (emptySpaces > 0) {
                    board[r + emptySpaces][c] = board[r][c].copy(row = r + emptySpaces)
                }
            }
            for (r in 0 until emptySpaces) {
                board[r][c] = Cell(row = r, col = c, type = CubeType.values().random())
            }
        }
    }
}