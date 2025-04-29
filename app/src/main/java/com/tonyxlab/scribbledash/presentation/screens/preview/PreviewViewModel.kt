package com.tonyxlab.scribbledash.presentation.screens.preview

import androidx.lifecycle.ViewModel
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreviewViewModel: ViewModel() {


    private val _previewUiState = MutableStateFlow(DrawUiState())
    val previewUiState = _previewUiState.asStateFlow()

    init {

    }

}