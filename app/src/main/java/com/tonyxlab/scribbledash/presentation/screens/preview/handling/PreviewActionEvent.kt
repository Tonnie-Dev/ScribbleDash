package com.tonyxlab.scribbledash.presentation.screens.preview.handling


sealed interface PreviewActionEvent {
    data object TryAgain: PreviewActionEvent
    data object Exit: PreviewActionEvent
}
