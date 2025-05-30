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
import com.tonyxlab.scribbledash.presentation.screens.draw.gamemode.speed.SpeedGameState
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawActionEvent
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiEvent
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState.RandomVectorData
import com.tonyxlab.utils.Constants
import com.tonyxlab.utils.calculatePathSimilarity
import com.tonyxlab.utils.toSvgPathStrings
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init
import timber.log.Timber


class DrawViewModel(
    private val randomVectorProvider: () -> RandomVectorData,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _drawingUiState = MutableStateFlow(DrawUiState())
    val drawingUiState = _drawingUiState.asStateFlow()

    private val _speedDrawState = MutableStateFlow(SpeedGameState.PreviewDrawing)
    val  speedDrawState = _speedDrawState.asStateFlow()

    private val _actionEvent = Channel<DrawActionEvent>()
    val actionEvent = _actionEvent.receiveAsFlow()

    var previewCountdownTimer: CountdownTimer? = null
    var speedDrawCountdownTimer: CountdownTimer = CountdownTimer(120)


    init {

        val navArgs =
            savedStateHandle.toRoute<Destinations.DrawScreenDestination>()

        val gameLevel = navArgs.gameLevel
        val gameMode = GameMode.getGameModeByTitle(navArgs.gameMode)
        updateGameLevel(gameLevel = gameLevel)
        updateGameMode(gameMode)

        updatePreviewTimeCountdown()
        pickNewRandomVector()
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

    private fun updateDrawTime() {
        if (_drawingUiState.value.gameMode is GameMode.SpeedDraw) {
            //speedDrawCountdownTimer?.stop()

            speedDrawCountdownTimer = CountdownTimer(120).also { timer ->

                if (timer.isRunning.not()){

                    Timber.i("updateDrawTime() has started the timer: ${timer.isRunning}")
                    timer.start()
                }

                timer.remainingSeconds.onEach { secs ->

                    _drawingUiState.update { it.copy(remainingSpeedDrawSeconds = secs) }
                }
                        .launchIn(viewModelScope)
            }

        }
    }

    private fun startDrawing() {

        Timber.i("Inside startDrawing(), time is ${speedDrawCountdownTimer?.isRunning}")

        updateDrawTime()
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

    private fun submitDrawing() {


        val similarityScore =

            calculatePathSimilarity(

                    referencePathStrings = _drawingUiState.value.currentSvgPath.paths,
                    userPathStrings = _drawingUiState.value.paths.toSvgPathStrings(),
                    difficulty = _drawingUiState.value.difficultyLevel,
                    canvasSize = _drawingUiState.value.canvasSize
            )

        _drawingUiState.update {
            it.copy(similarityScore = similarityScore)
        }

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

    fun pickNewRandomVector() {
        _drawingUiState.update { it.copy(currentSvgPath = randomVectorProvider()) }
    }

    private fun updateGameLevel(gameLevel: Int) {

        val level = DifficultyLevel.entries[gameLevel]

        _drawingUiState.update { it.copy(difficultyLevel = level) }
    }


    private fun updateGameMode(gameMode: GameMode) {

        _drawingUiState.update { it.copy(gameMode = gameMode) }
    }

    override fun onCleared() {
        super.onCleared()
        previewCountdownTimer?.pause()
    }


}

