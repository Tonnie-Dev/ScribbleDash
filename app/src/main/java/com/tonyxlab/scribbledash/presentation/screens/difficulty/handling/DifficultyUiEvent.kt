package com.tonyxlab.scribbledash.presentation.screens.difficulty.handling

import com.tonyxlab.scribbledash.domain.model.DifficultyLevel
import com.tonyxlab.scribbledash.presentation.core.base.handling.UiEvent

sealed interface DifficultyUiEvent : UiEvent {

    data class OnSelectBeginnerLevel(val difficultyLevel: DifficultyLevel) : DifficultyUiEvent
    data class OnSelectChallengingLevel(val difficultyLevel: DifficultyLevel) : DifficultyUiEvent
    data class OnSelectMasterLevel(val difficultyLevel: DifficultyLevel) : DifficultyUiEvent
    data class OnClose(val difficultyLevel: DifficultyLevel) : DifficultyUiEvent

}
