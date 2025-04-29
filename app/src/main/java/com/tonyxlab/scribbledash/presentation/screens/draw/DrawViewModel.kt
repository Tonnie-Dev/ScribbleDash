package com.tonyxlab.scribbledash.presentation.screens.draw

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState.RandomVectorData
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawingActionEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

class DrawViewModel(
    private val context: Context,
    private val randomVectorProvider: (Context) -> RandomVectorData


) : ViewModel(


) {


    private val _drawingUiState = MutableStateFlow(DrawUiState())
    val drawingUiState = _drawingUiState.asStateFlow()

    init {
        updateCountdown()
        pickNewRandomVector()
    }

    fun onEvent(event: DrawingActionEvent) {

        when (event) {
            is DrawingActionEvent.OnDraw -> onDraw(event.offset)
            DrawingActionEvent.OnStartNewPath -> startDrawing()
            DrawingActionEvent.OnEndPath -> endDrawing()
            DrawingActionEvent.OnUnDo -> unDoDrawing()
            DrawingActionEvent.OnRedo -> reDoDrawing()
            DrawingActionEvent.OnSubmitDrawing -> clearCanvas()
        }
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

    private fun startDrawing() {
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

    private fun clearCanvas() {


        _drawingUiState.update {
            it.copy(
                    currentPath = null,
                    paths = emptyList(),
                    undoStack = emptyList()
            )
        }

        updateButtonsState()
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

    private fun updateCountdown(secs: Int = 3) {

        viewModelScope.launch {

            for (i in secs downTo 0) {

                _drawingUiState.update { it.copy(remainingSecs = i) }
                delay(1_000)
            }
        }
    }

    fun pickNewRandomVector() {
        _drawingUiState.update { it.copy(currentSvgPath = randomVectorProvider(context)) }
    }


}

