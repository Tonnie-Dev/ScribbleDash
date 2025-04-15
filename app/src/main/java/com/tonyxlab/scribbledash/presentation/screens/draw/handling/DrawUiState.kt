package com.tonyxlab.scribbledash.presentation.screens.draw.handling

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Stable
data class DrawUiState(
    val selectedColor: Color = Color.Companion.Black,
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList()
) {

    @Stable
    data class PathData(
        val id: String,
        val color: Color,
        val path: List<Offset>
    )
}