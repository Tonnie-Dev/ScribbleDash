package com.tonyxlab.scribbledash.presentation.screens.difficulty.handling

import com.tonyxlab.scribbledash.presentation.core.base.handling.ActionEvent

sealed interface DifficultyActionEvent: ActionEvent{


    data object Exit: DifficultyActionEvent
    data class NavigateToDrawScreen(val mode: Int, val level: Int): DifficultyActionEvent
}
