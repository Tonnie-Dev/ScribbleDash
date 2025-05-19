package com.tonyxlab.scribbledash.presentation.screens.difficulty

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.tonyxlab.scribbledash.domain.model.GameMode
import com.tonyxlab.scribbledash.navigation.Destinations
import com.tonyxlab.scribbledash.presentation.core.base.BaseViewModel
import com.tonyxlab.scribbledash.presentation.screens.difficulty.handling.DifficultyActionEvent
import com.tonyxlab.scribbledash.presentation.screens.difficulty.handling.DifficultyUiEvent
import com.tonyxlab.scribbledash.presentation.screens.difficulty.handling.DifficultyUiState


typealias DifficultyBaseViewModel =
        BaseViewModel<DifficultyUiState, DifficultyUiEvent, DifficultyActionEvent>

class DifficultyViewModel(savedStateHandle: SavedStateHandle) : DifficultyBaseViewModel() {

    override val initialState: DifficultyUiState
        get() = DifficultyUiState()

    init {

        val mode = savedStateHandle.toRoute<Destinations.DifficultyDestination>()
        updateState {
            DifficultyUiState(gameMode = GameMode.getGameModeByTitle(mode.gameMode))
        }

    }

    override fun onEvent(event: DifficultyUiEvent) {

        when (event) {
            is DifficultyUiEvent.OnSelectBeginnerLevel -> navigateToDraw(
                    mode = currentState.gameMode.title,
                    level = event.difficultyLevel.ordinal
            )

            is DifficultyUiEvent.OnSelectChallengingLevel -> navigateToDraw(
                    mode = currentState.gameMode.title,
                    level = event.difficultyLevel.ordinal
            )

            is DifficultyUiEvent.OnSelectMasterLevel -> navigateToDraw(
                    mode = currentState.gameMode.title,
                    level = event.difficultyLevel.ordinal
            )

            is DifficultyUiEvent.OnClose -> exit()
        }
    }

    private fun navigateToDraw(mode: Int, level: Int) {
        sendActionEvent(
                DifficultyActionEvent.NavigateToDrawScreen(
                        mode = mode,
                        level = level
                )
        )

    }

    private fun exit() {

        sendActionEvent(DifficultyActionEvent.Exit)

    }
}