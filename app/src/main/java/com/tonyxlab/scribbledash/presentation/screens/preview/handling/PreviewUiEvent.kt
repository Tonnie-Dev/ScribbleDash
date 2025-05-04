package com.tonyxlab.scribbledash.presentation.screens.preview.handling

import com.tonyxlab.scribbledash.presentation.core.base.handling.UiEvent

sealed interface PreviewUiEvent: UiEvent {
    data object OnTryAgainButtonClick: PreviewUiEvent
    data object OnCloseButtonClick: PreviewUiEvent
}