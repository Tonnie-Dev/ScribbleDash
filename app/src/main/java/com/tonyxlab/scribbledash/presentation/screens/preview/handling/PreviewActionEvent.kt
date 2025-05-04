package com.tonyxlab.scribbledash.presentation.screens.preview.handling

import com.tonyxlab.scribbledash.presentation.core.base.handling.ActionEvent


sealed interface PreviewActionEvent: ActionEvent{
    data object TryAgain: PreviewActionEvent
    data object Exit: PreviewActionEvent
}
