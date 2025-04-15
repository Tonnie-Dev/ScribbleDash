package com.tonyxlab.scribbledash.presentation.screens.draw.handling

import androidx.compose.ui.geometry.Offset

sealed interface DrawingAction {
    data object OnStartNewPath : DrawingAction
    data class OnDraw(val offset: Offset) : DrawingAction
    data object OnEndPath : DrawingAction
    data object OnClearCanvas : DrawingAction
    data object OnUnDo : DrawingAction
    data object OnRedo : DrawingAction
}