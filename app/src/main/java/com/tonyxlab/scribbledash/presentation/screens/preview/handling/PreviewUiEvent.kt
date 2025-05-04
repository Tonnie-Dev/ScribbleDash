package com.tonyxlab.scribbledash.presentation.screens.preview.handling

sealed interface PreviewUiEvent {
    data object OnTryAgainButtonClick: PreviewUiEvent
    data object OnCloseButtonClick: PreviewUiEvent
}