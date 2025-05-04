package com.tonyxlab.scribbledash.presentation.screens.preview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tonyxlab.scribbledash.navigation.Destinations
import com.tonyxlab.scribbledash.presentation.screens.preview.handling.PreviewActionEvent
import com.tonyxlab.scribbledash.presentation.screens.preview.handling.PreviewUiEvent
import com.tonyxlab.scribbledash.presentation.screens.preview.handling.PreviewUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PreviewViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {


    private val _previewUiState = MutableStateFlow(PreviewUiState())
    val previewUiState = _previewUiState.asStateFlow()

    private val _previewActionEvent = Channel<PreviewActionEvent>()
    val previewActionEvent = _previewActionEvent.receiveAsFlow()

    init {

        val data = savedStateHandle.toRoute<Destinations.PreviewDestination>()
        updatePreviewState(
                PreviewUiState(
                        score = data.similarityScore,
                        sampleSvgStrings = data.sampleSvgStrings,
                        userPathStrings = data.userPathStrings,
                        viewPortWidth = data.viewPortWidth,
                        viewPortHeight = data.viewPortHeight,

                )
        )

    }

    fun onEvent(event: PreviewUiEvent) {

        when(event){

            PreviewUiEvent.OnTryAgainButtonClick -> onTryAgain()
            PreviewUiEvent.OnCloseButtonClick -> onClose()
        }
    }


    private fun updatePreviewState(previewUiState: PreviewUiState) {


        _previewUiState.update { previewUiState }
    }

    private fun onTryAgain() {

        viewModelScope.launch {

            _previewActionEvent.send(PreviewActionEvent.TryAgain)
        }
    }

    private fun onClose() {

        viewModelScope.launch {

            _previewActionEvent.send(PreviewActionEvent.Exit)
        }
    }


}