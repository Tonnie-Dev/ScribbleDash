package com.tonyxlab.scribbledash.presentation.screens.stats.handling

import com.tonyxlab.scribbledash.presentation.core.base.handling.UiState

data class StatsUiState(
    val highestSpeedModeAccuracy: Int = 0,
    val highestEndlessModeAccuracy: Int = 0,
    val mostMeh: Int = 0,
    val mostEndlessModeCompletion: Int = 0
): UiState
