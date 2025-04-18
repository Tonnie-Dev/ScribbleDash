package com.tonyxlab.scribbledash.presentation.screens.draw.handling

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Stable
data class DrawUiState(
    val selectedColor: Color = Color.Black,
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList(),
    val buttonsState: ButtonsState = ButtonsState(),
    val undoStack: List<PathData> = emptyList(),
    val remainingSecs: Int = 0
) {
    @Stable
    data class PathData(
        val id: Long = 0L,
        val color: Color = Color.Black,
        val path: List<Offset> = emptyList()
    )

    @Stable
    data class ButtonsState(
        val undoButtonEnabled: Boolean = false,
        val redoButtonEnabled: Boolean = false,
        val clearButtonEnabled: Boolean = false
    )


}