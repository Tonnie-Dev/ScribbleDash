package com.tonyxlab.scribbledash.presentation.screens.preview.handling

import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState

data class PreviewUiState( val score: Int,
                           val sampleSvgData: List<String>,
                           val pathData: List<DrawUiState.PathData>,)