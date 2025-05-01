package com.tonyxlab.scribbledash.presentation.screens.preview.handling

import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState

data class PreviewUiState(
    val score: Int = 0,
    val sampleSvgStrings: List<String> = emptyList(),
    val userPathStrings: List<String> = emptyList(),
    val viewPortWidth: Float = 0f,
    val viewPortHeight: Float = 0f
)