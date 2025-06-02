package com.tonyxlab.scribbledash.presentation.screens.draw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.domain.model.Game
import com.tonyxlab.scribbledash.domain.model.GameMode
import com.tonyxlab.scribbledash.presentation.core.components.AppButton
import com.tonyxlab.scribbledash.presentation.core.components.AppHeaderText
import com.tonyxlab.scribbledash.presentation.core.components.AppIcon
import com.tonyxlab.scribbledash.presentation.core.components.AppLabelText
import com.tonyxlab.scribbledash.presentation.core.components.DrawingCanvas
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.screens.draw.components.CounterRow
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawActionEvent
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiEvent
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState.PathData
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme
import com.tonyxlab.scribbledash.presentation.theme.Success
import com.tonyxlab.utils.drawCustomPaths
import com.tonyxlab.utils.drawSvgVector
import com.tonyxlab.utils.toSvgPathStrings
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun DrawScreen(
    gameMode: GameMode,
    onClose: () -> Unit,
    onSubmit: (List<String>, List<String>, Float, Float, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DrawViewModel = koinViewModel(),

    ) {

    val state = viewModel.drawingUiState.collectAsStateWithLifecycle().value

    LaunchedEffect(true) {

        viewModel.actionEvent.collect {

            when (it) {

                is DrawActionEvent.NavigateToPreviewScreen -> onSubmit(
                        it.sampleSvgPathData,
                        it.userPathData,
                        it.viewPortWidth,
                        it.viewPortHeight,
                        it.similarityScore
                )
            }
        }

    }

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->

        DrawScreenContent(
                modifier = modifier.padding(innerPadding),
                game = Game().copy(mode = gameMode, remainingSecs = state.remainingSpeedDrawSeconds),
                remainingSecs = state.remainingPreviewSeconds,
                currentPath = state.currentPath,
                paths = state.paths,
                onClose = onClose,
                buttonsState = state.buttonsState,
                sampleSvgPath = state.currentSvgPath.paths,
                viewPortWidth = state.currentSvgPath.viewportWidth,
                viewPortHeight = state.currentSvgPath.viewportHeight,
                onAction = viewModel::onEvent
        )
    }
}

@Composable
fun DrawScreenContent(
    game: Game,
    currentPath: PathData?,
    paths: List<PathData>,
    sampleSvgPath: List<String>,
    viewPortWidth: Float,
    viewPortHeight: Float,
    onClose: () -> Unit,
    onAction: (DrawUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    buttonsState: DrawUiState.ButtonsState = DrawUiState.ButtonsState(),
    remainingSecs: Int
) {


    val canDraw = remainingSecs < 1


    Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CounterRow(
                game = game,
                onClose = onClose
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceDoubleDp * 41))

        AppHeaderText(
                modifier = Modifier.padding(
                        bottom = MaterialTheme.spacing.spaceLarge,
                ),
                textStyle = MaterialTheme.typography.displayMedium,
                text = if (canDraw)
                    stringResource(R.string.headline_time_to_draw)
                else
                    stringResource(R.string.headline_ready_set)
        )

        Column(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.spaceTen * 3),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceDoubleDp)
        ) {
            DrawingCanvas(
                    modifier = Modifier.onSizeChanged { size ->
                        onAction(
                                DrawUiEvent.OnCanvasSizeChanged(
                                        Size(
                                                size.width.toFloat(),
                                                size.height.toFloat()
                                        )
                                )
                        )
                    },

                    onAction = onAction,
                    canDraw = canDraw,
                    onCustomDraw = {

                      /*  paths.fastForEach { pathData ->
                            drawCustomPaths(path = pathData.path, color = pathData.color)
                        }

                        currentPath?.let {
                            drawCustomPaths(path = it.path, color = it.color)
                        }*/

                        if (canDraw) {

                            paths.fastForEach { pathData ->
                                drawCustomPaths(path = pathData.path, color = pathData.color)
                            }

                            currentPath?.let {
                                drawCustomPaths(path = it.path, color = it.color)
                            }

                        } else {

                            drawSvgVector(
                                    vectorPaths = sampleSvgPath,
                                    viewportWidth = viewPortWidth,
                                    viewportHeight = viewPortHeight
                            )
                        }

                    }

            )
            AppLabelText(
                    if (canDraw)
                        stringResource(R.string.text_your_drawing)
                    else
                        stringResource(R.string.text_example)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (canDraw) {

            Row(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.spaceTen * 3),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceTwelve)
                ) {
                    AppIcon(
                            enabled = buttonsState.undoButtonEnabled,
                            icon = R.drawable.ic_reply,
                            onClick = { onAction(DrawUiEvent.OnUnDo) }
                    )

                    AppIcon(
                            enabled = buttonsState.redoButtonEnabled,
                            icon = R.drawable.ic_forward,
                            onClick = { onAction(DrawUiEvent.OnRedo) }
                    )
                }
                AppButton(
                        modifier = Modifier
                                .height(MaterialTheme.spacing.spaceExtraLarge)
                                .width(IntrinsicSize.Min),
                        enabled = buttonsState.submitButtonEnabled,
                        buttonText = stringResource(R.string.button_text_done),
                        containerColor = Success,
                        onClick = {

                            onAction(
                                    DrawUiEvent.OnSubmitDrawing(
                                            sampleSvgPathData = sampleSvgPath,
                                            userPathData = paths.toSvgPathStrings(),
                                            viewPortWidth = viewPortWidth,
                                            viewPortHeight = viewPortHeight
                                    )
                            )
                        }
                )
            }
        } else {
            AppHeaderText(text = stringResource(R.string.text_seconds_left, remainingSecs))
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
                    sampleSvgPath = emptyList(),
                    viewPortWidth = 0f,
                    viewPortHeight = 0f,
                    onAction = {},
                    game = Game()
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
                    sampleSvgPath = emptyList(),
                    viewPortWidth = 0f,
                    viewPortHeight = 0f,
                    onAction = {},
                    game = Game(mode = GameMode.SpeedDraw)
            )
        }
    }
}


