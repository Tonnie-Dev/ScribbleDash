package com.tonyxlab.scribbledash.presentation.screens.preview

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.tonyxlab.scribbledash.navigation.Destinations
import com.tonyxlab.scribbledash.presentation.core.base.BaseViewModel
import com.tonyxlab.scribbledash.presentation.screens.preview.handling.PreviewActionEvent
import com.tonyxlab.scribbledash.presentation.screens.preview.handling.PreviewUiEvent
import com.tonyxlab.scribbledash.presentation.screens.preview.handling.PreviewUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

typealias PreviewBaseViewModel = BaseViewModel<PreviewUiState, PreviewUiEvent, PreviewActionEvent>

class PreviewViewModel(
    savedStateHandle: SavedStateHandle,

    ) : PreviewBaseViewModel() {

    override val initialState: PreviewUiState
        get() = PreviewUiState()

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

    override fun onEvent(event: PreviewUiEvent) {

        when (event) {
            PreviewUiEvent.OnTryAgainButtonClick -> onTryAgain()
            PreviewUiEvent.OnCloseButtonClick -> onClose()
        }
    }

    private fun updatePreviewState(previewUiState: PreviewUiState) {
        updateState { previewUiState }
    }

    private fun onTryAgain() {
        sendActionEvent(PreviewActionEvent.TryAgain)
    }

    private fun onClose() {
        sendActionEvent(PreviewActionEvent.Exit)
    }

}