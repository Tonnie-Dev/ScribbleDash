package com.tonyxlab.scribbledash.presentation.screens.draw

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawingActionEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

class DrawViewModel : ViewModel() {


    init {
        Timber.i("Init called in the viewmodel")
    }
    private val _drawingUiState = MutableStateFlow(DrawUiState())
    val drawingUiState = _drawingUiState.asStateFlow()


    fun onEvent(event: DrawingActionEvent) {

        Timber.i("OnEvent Called")
        when (event) {
            is DrawingActionEvent.OnDraw -> onDraw(event.offset)
            DrawingActionEvent.OnStartNewPath -> startDrawing()
            DrawingActionEvent.OnEndPath -> endDrawing()
            DrawingActionEvent.OnUnDo -> unDoDrawing()
            DrawingActionEvent.OnRedo -> reDoDrawing()
            DrawingActionEvent.OnClearCanvas -> clearCanvas()
        }
    }

    private fun onDraw(offset: Offset) {

        Timber.i("onDraw Called")
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

        Timber.i("Start Drawing")
        _drawingUiState.update {

            it.copy(
                    currentPath = DrawUiState.PathData(id = System.currentTimeMillis()),
                    paths = emptyList()
            )
        }
    }

    private fun endDrawing() {

        Timber.i("End Drawing")

        val currentPathData = _drawingUiState.value.currentPath ?: return

        _drawingUiState.update { it.copy(currentPath = null, paths = it.paths + currentPathData) }
    }

    private fun unDoDrawing() {

        Timber.i("Undo")
        val currentPaths = _drawingUiState.value.paths
        if (currentPaths.isNotEmpty()) {
            val lastPath = currentPaths.last()
            _drawingUiState.update {
                it.copy(
                        paths = currentPaths.dropLast(1),
                        undoStack = (it.undoStack + lastPath).takeLast(5) // Add to undo stack, limit to 5
                )
            }
        }
    }

    private fun reDoDrawing() {

        Timber.i("Redo called")
        val undoStack = _drawingUiState.value.undoStack
        if (undoStack.isNotEmpty()) {
            val lastUndonePath = undoStack.last()
            _drawingUiState.update {
                it.copy(
                        paths = it.paths + lastUndonePath,
                        undoStack = undoStack.dropLast(1) // Remove the restored path from the stack
                )
            }
        }
    }

    private fun clearCanvas() {

        Timber.i("Clear Canvas Called")
        _drawingUiState.update {
            it.copy(
                    currentPath = null,
                    paths = emptyList(),
                    undoStack = emptyList()
            )
        }
    }


}