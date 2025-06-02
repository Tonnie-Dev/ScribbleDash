package com.tonyxlab.scribbledash.presentation.screens.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tonyxlab.scribbledash.domain.model.DifficultyLevel
import com.tonyxlab.scribbledash.domain.model.GameMode
import com.tonyxlab.scribbledash.navigation.Destinations
import com.tonyxlab.scribbledash.presentation.core.utils.CountdownTimer
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawActionEvent
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiEvent
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState.RandomVectorData
import com.tonyxlab.utils.Constants
import com.tonyxlab.utils.calculatePathSimilarity
import com.tonyxlab.utils.toSvgPathStrings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber


class DrawViewModel(
    private val randomVectorProvider: () -> RandomVectorData,
    private val vectorList: List<RandomVectorData>,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var currentIndex = 0
    private val _drawingUiState = MutableStateFlow(DrawUiState())
    val drawingUiState = _drawingUiState.asStateFlow()

    private val _actionEvent = Channel<DrawActionEvent>()
    val actionEvent = _actionEvent.receiveAsFlow()

    var previewCountdownTimer: CountdownTimer? = null
    var speedDrawCountdownTimer: CountdownTimer? = null

    private var drawingsCount = 0

    init {

        val navArgs =
            savedStateHandle.toRoute<Destinations.DrawScreenDestination>()

        val gameLevel = navArgs.gameLevel
        val gameMode = GameMode.getGameModeByTitle(navArgs.gameMode)
        updateGameLevel(gameLevel = gameLevel)
        updateGameMode(gameMode)

        updatePreviewTimeCountdown()
        fetchRandomVector()
    }

    fun onEvent(event: DrawUiEvent) {

        when (event) {
            is DrawUiEvent.OnDraw -> onDraw(event.offset)
            DrawUiEvent.OnStartNewPath -> startDrawing()
            DrawUiEvent.OnEndPath -> endDrawing()
            DrawUiEvent.OnUnDo -> unDoDrawing()
            DrawUiEvent.OnRedo -> reDoDrawing()
            is DrawUiEvent.OnSubmitDrawing -> submitDrawing()
            is DrawUiEvent.OnCanvasSizeChanged -> updateSize(event.size)
        }
    }

    private fun updateSize(size: Size) {

        _drawingUiState.update { it.copy(canvasSize = size) }

    }

    private fun onDraw(offset: Offset) {
        val currentPathData = _drawingUiState.value.currentPath ?: return


        _drawingUiState.update {
            it.copy(
                    currentPath = currentPathData.copy(
                            path = currentPathData.path.plus(
                                    offset
                            )
                    )
            )
        }

    }

    private fun launchSpeedDrawTimer() {

        speedDrawCountdownTimer?.resume()
        Timber.i("Inside LSDT - prepping, null check: ${speedDrawCountdownTimer == null} ")
        val gameMode = _drawingUiState.value.gameMode
        if (gameMode !is GameMode.SpeedDraw || speedDrawCountdownTimer != null) return


        Timber.i("Inside LSDT - creating new timer ")
        speedDrawCountdownTimer = CountdownTimer(Constants.SPEED_DRAW_TIME_LIMIT).also { timer ->

            timer.start()
            timer.remainingSeconds.onEach { secs ->

                _drawingUiState.update { it.copy(remainingSpeedDrawSeconds = secs) }
            }
                    .launchIn(viewModelScope)

        }

    }

    private fun startDrawing() {
        launchSpeedDrawTimer()



        _drawingUiState.update {

            it.copy(currentPath = DrawUiState.PathData(id = System.currentTimeMillis()))
        }

        updateButtonsState()
    }

    private fun endDrawing() {

        val currentPathData = drawingUiState.value.currentPath ?: return

        _drawingUiState.update {
            it.copy(
                    currentPath = null,
                    paths = it.paths + currentPathData
            )
        }

        updateButtonsState()

    }

    private fun unDoDrawing() {

        val currentPaths = _drawingUiState.value.paths
        if (currentPaths.isNotEmpty()) {
            val lastPath = currentPaths.last()
            _drawingUiState.update {
                it.copy(
                        paths = currentPaths.dropLast(1),
                        undoStack = (it.undoStack + lastPath).takeLast(5) // Add to undo stack, limit to 5
                )
            }

            updateButtonsState()
        }
    }

    private fun reDoDrawing() {

        val undoStack = _drawingUiState.value.undoStack
        if (undoStack.isNotEmpty()) {
            val lastUndonePath = undoStack.last()
            _drawingUiState.update {
                it.copy(
                        paths = it.paths + lastUndonePath,
                        undoStack = undoStack.dropLast(1)
                )
            }

            updateButtonsState()
        }
    }


    private suspend fun calculateSimilarity(): Int = withContext(Dispatchers.Default) {

        calculatePathSimilarity(

                referencePathStrings = _drawingUiState.value.currentSvgPath.paths,
                userPathStrings = _drawingUiState.value.paths.toSvgPathStrings(),
                difficulty = _drawingUiState.value.difficultyLevel,
                canvasSize = _drawingUiState.value.canvasSize
        )

    }


    private fun submitDrawing() {

        when (_drawingUiState.value.gameMode) {

            is GameMode.SpeedDraw -> launchSpeedGameFlow()
            is GameMode.EndlessMode -> launchSpeedGameFlow()
            else -> launchOneWonderGameFlow()
        }


    }

    private fun launchOneWonderGameFlow() {
        viewModelScope.launch {


            _actionEvent.send(
                    DrawActionEvent.NavigateToPreviewScreen(
                            sampleSvgPathData = _drawingUiState.value.currentSvgPath.paths,
                            userPathData = _drawingUiState.value.paths.toSvgPathStrings(),
                            viewPortWidth = _drawingUiState.value.currentSvgPath.viewportWidth,
                            viewPortHeight = _drawingUiState.value.currentSvgPath.viewportHeight,
                            similarityScore = _drawingUiState.value.similarityScore
                    )
            )
        }
    }

    private fun launchSpeedGameFlow() {
        speedDrawCountdownTimer?.pause()

        viewModelScope.launch {
            val score = calculateSimilarity()
            _drawingUiState.update {
                it.copy(similarityScore = score)
            }


        }

        updateDrawingsCount()
        clearPaths()
        fetchRandomVector()
        updatePreviewTimeCountdown()
    }


    private fun updateDrawingsCount() {

        if (_drawingUiState.value.similarityScore >= 40) {

            drawingsCount++
            _drawingUiState.update { it.copy(counter = drawingsCount) }

        }

    }

    private fun updateButtonsState() {
        _drawingUiState.update {
            it.copy(
                    buttonsState = it.buttonsState.copy(
                            undoButtonEnabled = it.paths.isNotEmpty(),
                            redoButtonEnabled = it.undoStack.isNotEmpty(),
                            submitButtonEnabled = it.paths.isNotEmpty() || it.currentPath != null
                    )
            )
        }
    }

    private fun updatePreviewTimeCountdown() {

        previewCountdownTimer?.stop() // // Clean up the old one if needed


        previewCountdownTimer =
            CountdownTimer(totalSeconds = Constants.PREVIEW_TIME_IN_SECS).also { timer ->

                timer.start()

                timer.remainingSeconds.onEach { secs ->

                    _drawingUiState.update { it.copy(remainingPreviewSeconds = secs) }

                }
                        .launchIn(viewModelScope)
            }


    }

    private fun fetchRandomVector() {
        if (vectorList.isEmpty()) {
            _drawingUiState.update {
                it.copy(
                        currentSvgPath = RandomVectorData(
                                emptyList(),
                                0f,
                                0f
                        )
                )
            }
            return
        }

        val nextVector = vectorList[currentIndex]
        _drawingUiState.update { it.copy(currentSvgPath = nextVector) }

        currentIndex = (currentIndex + 1) % vectorList.size
        // Optional: reshuffle when looping back
        if (currentIndex == 0) {
            (vectorList as MutableList).shuffle()
        }

        // _drawingUiState.update { it.copy(currentSvgPath = randomVectorProvider()) }
    }

    private fun updateGameLevel(gameLevel: Int) {

        val level = DifficultyLevel.entries[gameLevel]

        _drawingUiState.update { it.copy(difficultyLevel = level) }
    }


    private fun updateGameMode(gameMode: GameMode) {

        _drawingUiState.update { it.copy(gameMode = gameMode) }
    }

    private fun clearPaths() {

        _drawingUiState.update {
            it.copy(
                    currentPath = null,
                    paths = emptyList(),
                    undoStack = emptyList()
            )
        }

    }

    override fun onCleared() {
        super.onCleared()
        previewCountdownTimer?.pause()

    }

}