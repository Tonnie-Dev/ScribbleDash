package com.tonyxlab.scribbledash.presentation.screens.draw.handling

import androidx.compose.ui.geometry.Offset

sealed interface DrawUiEvent {
    data object OnStartNewPath : DrawUiEvent
    data class OnDraw(val offset: Offset) : DrawUiEvent
    data object OnEndPath : DrawUiEvent
    data class OnSubmitDrawing(
        val sampleSvgPathData: List<String>,
        val userPathData: List<String>,
        val viewPortWidth: Float,
        val viewPortHeight: Float
    ) : DrawUiEvent

    data object OnUnDo : DrawUiEvent
    data object OnRedo : DrawUiEvent
}