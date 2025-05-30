package com.tonyxlab.scribbledash.presentation.screens.draw.gamemode.speed

sealed class SpeedGameState {
    data object PreviewDrawing : SpeedGameState()
    data object StartDrawing : SpeedGameState()
    data object ContinueDrawing : SpeedGameState()
    data class FinishDrawing(
        val averageScore: Int,
        val mehPlusCount: Int,
        val isMehPlusHighScore: Boolean,
        val isAverageAccuracyHighScore: Boolean
    ) : SpeedGameState()
}