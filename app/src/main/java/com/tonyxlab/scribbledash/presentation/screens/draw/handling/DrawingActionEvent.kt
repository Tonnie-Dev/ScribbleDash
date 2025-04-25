package com.tonyxlab.scribbledash.presentation.screens.draw.handling

import androidx.compose.ui.geometry.Offset

sealed interface DrawingActionEvent {
    data object OnStartNewPath : DrawingActionEvent
    data class OnDraw(val offset: Offset) : DrawingActionEvent
    data object OnEndPath : DrawingActionEvent
    data object OnSubmitDrawing : DrawingActionEvent
    data object OnUnDo : DrawingActionEvent
    data object OnRedo : DrawingActionEvent
}