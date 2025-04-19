package com.tonyxlab.scribbledash.presentation.screens.draw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.core.components.AppButton
import com.tonyxlab.scribbledash.presentation.core.components.AppCloseIcon
import com.tonyxlab.scribbledash.presentation.core.components.AppHeadlineText
import com.tonyxlab.scribbledash.presentation.core.components.AppIcon
import com.tonyxlab.scribbledash.presentation.core.components.AppLabelText
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.screens.draw.components.DrawingCanvas
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState.PathData
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawingActionEvent
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme
import com.tonyxlab.scribbledash.presentation.theme.Success
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun DrawScreen(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DrawViewModel = koinViewModel()
) {

    val state = viewModel.drawingUiState.collectAsStateWithLifecycle().value

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->

        DrawScreenContent(
                modifier = modifier.padding(innerPadding),
                remainingSecs = state.remainingSecs,
                currentPath = state.currentPath,
                paths = state.paths,
                onClose = onClose,
                buttonsState = state.buttonsState,
                onAction = viewModel::onEvent
        )
    }
}

@Composable
fun DrawScreenContent(
    currentPath: PathData?,
    paths: List<PathData>,
    onClose: () -> Unit,
    onAction: (DrawingActionEvent) -> Unit,
    modifier: Modifier = Modifier,
    buttonsState: DrawUiState.ButtonsState = DrawUiState.ButtonsState(),
    remainingSecs: Int
) {

    val canDraw by remember(remainingSecs) {

        derivedStateOf { remainingSecs < 1 }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        AppCloseIcon(
                modifier = Modifier.padding(
                        top = MaterialTheme.spacing.spaceSmall,
                        bottom = MaterialTheme.spacing.spaceTwelve * 4
                ),
                onClose = onClose
        )
        Column(
                modifier = modifier.padding(MaterialTheme.spacing.spaceTen * 3),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {


            AppHeadlineText(
                    modifier = Modifier.padding(
                            bottom = MaterialTheme.spacing.spaceTwelve * 4,
                    ),
                    textStyle = MaterialTheme.typography.displayMedium,
                    text = if (canDraw)
                        stringResource(R.string.headline_time_to_draw)
                    else
                        stringResource(R.string.headline_ready_set)
            )

            Column(
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceOneTwentyEight),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceDoubleDp)
            ) {
                DrawingCanvas(
                        currentPath = currentPath,
                        paths = paths,
                        onAction = onAction,
                        context = LocalContext.current,
                        canDraw = canDraw
                )
                AppLabelText(
                        if (canDraw)
                            stringResource(R.string.text_your_drawing)
                        else
                            stringResource(R.string.text_example)
                )
            }

            if (canDraw) {

                Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceTwelve)) {

                    AppIcon(
                            enabled = buttonsState.undoButtonEnabled,
                            icon = R.drawable.ic_reply,
                            onClick = { onAction(DrawingActionEvent.OnUnDo) }
                    )

                    AppIcon(
                            enabled = buttonsState.redoButtonEnabled,
                            icon = R.drawable.ic_forward,
                            onClick = { onAction(DrawingActionEvent.OnRedo) }
                    )
                    AppButton(
                            modifier = Modifier.height(MaterialTheme.spacing.spaceExtraLarge),
                            enabled = buttonsState.clearButtonEnabled,
                            buttonText = stringResource(R.string.button_text_clear_canvas),
                            contentColor = Success,
                            onClick = { onAction(DrawingActionEvent.OnClearCanvas) }
                    )
                }
            } else {
                AppHeadlineText(text = stringResource(R.string.text_seconds_left, remainingSecs))
            }
        }
    }

}


@PreviewLightDark
@Composable
private fun DrawScreenContentWithTwoSecsPreview() {

    ScribbleDashTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
        ) {
            DrawScreenContent(
                    remainingSecs = 2,
                    onClose = {},
                    currentPath = null,
                    paths = emptyList(),
                    onAction = {}
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun DrawScreenContentWithZeroSecPreview() {

    ScribbleDashTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
        ) {
            DrawScreenContent(
                    remainingSecs = 0,
                    onClose = {},
                    currentPath = null,
                    paths = emptyList(),
                    onAction = {}
            )
        }
    }
}


