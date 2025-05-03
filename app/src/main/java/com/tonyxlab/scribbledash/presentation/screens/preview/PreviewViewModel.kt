package com.tonyxlab.scribbledash.presentation.screens.preview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.tonyxlab.scribbledash.navigation.Destinations
import com.tonyxlab.scribbledash.presentation.screens.preview.handling.PreviewUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PreviewViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {


    private val _previewUiState = MutableStateFlow(PreviewUiState())
    val previewUiState = _previewUiState.asStateFlow()

    init {

        val data = savedStateHandle.toRoute<Destinations.PreviewDestination>()
        updatePreviewState(
                PreviewUiState(
                        score = data.similarityScore,
                        sampleSvgStrings = data.sampleSvgStrings,
                        userPathStrings = data.userPathStrings,
                        viewPortWidth = data.viewPortWidth,
                        viewPortHeight = data.viewPortHeight
                )
        )

    }


    private fun updatePreviewState(previewUiState: PreviewUiState) {


        _previewUiState.update { previewUiState }
    }

}