package com.tonyxlab.scribbledash.presentation.screens.difficulty.handling

import androidx.compose.runtime.Stable
import com.tonyxlab.scribbledash.domain.model.DifficultyLevel
import com.tonyxlab.scribbledash.domain.model.GameMode
import com.tonyxlab.scribbledash.presentation.core.base.handling.UiState

@Stable
data class DifficultyUiState(
    val gameMode: GameMode = GameMode.OneRoundWonder,
    val difficultyLevel: DifficultyLevel = DifficultyLevel.BEGINNER
) : UiState