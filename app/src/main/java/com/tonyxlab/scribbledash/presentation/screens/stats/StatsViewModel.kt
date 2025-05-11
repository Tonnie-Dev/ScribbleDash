package com.tonyxlab.scribbledash.presentation.screens.stats

import com.tonyxlab.scribbledash.presentation.core.base.BaseViewModel
import com.tonyxlab.scribbledash.presentation.screens.stats.handling.StatsActionEvent
import com.tonyxlab.scribbledash.presentation.screens.stats.handling.StatsUiEvent
import com.tonyxlab.scribbledash.presentation.screens.stats.handling.StatsUiState


typealias StatsBaseViewModel = BaseViewModel<StatsUiState, StatsUiEvent, StatsActionEvent>
class StatsViewModel : StatsBaseViewModel(){


    override val initialState: StatsUiState
        get() = StatsUiState()

    override fun onEvent(event: StatsUiEvent) {
        TODO("Not yet implemented")
    }
}