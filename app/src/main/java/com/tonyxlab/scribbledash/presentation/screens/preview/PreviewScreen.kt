package com.tonyxlab.scribbledash.presentation.screens.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.navigation.NavOperations
import com.tonyxlab.scribbledash.presentation.core.base.BaseContentLayout
import com.tonyxlab.scribbledash.presentation.core.base.handling.UiState
import com.tonyxlab.scribbledash.presentation.core.components.AppBodyText
import com.tonyxlab.scribbledash.presentation.core.components.AppButton
import com.tonyxlab.scribbledash.presentation.core.components.AppCloseIcon
import com.tonyxlab.scribbledash.presentation.core.components.AppHeaderText
import com.tonyxlab.scribbledash.presentation.core.components.AppLabelText
import com.tonyxlab.scribbledash.presentation.core.components.DrawingCanvas
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.screens.preview.handling.PreviewActionEvent
import com.tonyxlab.scribbledash.presentation.screens.preview.handling.PreviewUiEvent
import com.tonyxlab.scribbledash.presentation.screens.preview.handling.PreviewUiState
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme
import com.tonyxlab.utils.FeedbackProvider
import com.tonyxlab.utils.centerAndScaleToFit
import com.tonyxlab.utils.drawCustomPaths
import com.tonyxlab.utils.drawSvgVector
import com.tonyxlab.utils.toOffsetPaths
import org.koin.androidx.compose.koinViewModel

@Composable
fun PreviewScreen(
    modifier: Modifier = Modifier,
    navOperations: NavOperations,
    viewModel: PreviewViewModel = koinViewModel()
) {
    BaseContentLayout(
            viewModel = viewModel,

            actionEventHandler = { _, actionEvent ->

                when (actionEvent) {
                    PreviewActionEvent.TryAgain -> {
                        navOperations.navigateToDifficultyScreen()
                    }

                    PreviewActionEvent.Exit -> {
                        navOperations.popBackStack()
                    }
                }


            }) { previewUiState ->

        PreviewContentScreen(
                modifier = modifier,
                uiState = previewUiState,
                onEvent = viewModel::onEvent
        )
    }


    /*
        val state by viewModel.previewUiState.collectAsStateWithLifecycle()
        val context = LocalContext.current


        LaunchedEffect(true) {

            viewModel.previewActionEvent.collect {

                when (it) {

                    PreviewActionEvent.TryAgain -> {
                        navOperations.navigateToDifficultyScreen()
                    }

                    PreviewActionEvent.Exit -> {
                        navOperations.popBackStack()
                    }
                }
            }
        }
        Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->


            PreviewContentScreen(
                    modifier = modifier.padding(innerPadding),
                    uiState = ,

                    onEvent = viewModel::onEvent
            )
        }*/

}

@Composable
fun PreviewContentScreen(
    uiState: PreviewUiState,
    onEvent: (PreviewUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val feedback = FeedbackProvider.getFeedback(context, uiState.score)

    Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppCloseIcon(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceDoubleDp * 42),
                onClose = { onEvent(PreviewUiEvent.OnCloseButtonClick) }
        )

        AppHeaderText(
                text = uiState.score.toString()
                        .plus("%"),
                textStyle = MaterialTheme.typography.displayLarge
        )

        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                        MaterialTheme.spacing.spaceMedium,
                        alignment = Alignment.CenterHorizontally
                )
        ) {

            PreviewItem(
                    modifier = Modifier
                            .graphicsLayer {
                                rotationZ = -10f
                            },
                    text = stringResource(id = R.string.text_example)
            ) {

                drawSvgVector(
                        vectorPaths = uiState.sampleSvgStrings,
                        viewportWidth = uiState.viewPortWidth,
                        viewportHeight = uiState.viewPortHeight
                )
            }

            PreviewItem(
                    modifier = Modifier
                            .graphicsLayer {
                                rotationZ = 10f
                            },
                    text = stringResource(id = R.string.text_drawing)
            ) {

                val mergedPath: List<Offset> = uiState.userPathStrings.toOffsetPaths()
                        .flatten()
                val centeredPaths = mergedPath.centerAndScaleToFit(this.size)

                drawCustomPaths(path = centeredPaths, thickness = 1f)
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceLarge))

        AppHeaderText(feedback.first, textStyle = MaterialTheme.typography.headlineLarge)

        AppBodyText(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceOneHundredFifty),
                text = feedback.second
        )

        Spacer(modifier = Modifier.weight(1f))

        AppButton(

                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceDoubleDp * 11),
                containerColor = MaterialTheme.colorScheme.primary,
                buttonText = stringResource(R.string.button_text_try_again)
        ) { onEvent(PreviewUiEvent.OnTryAgainButtonClick) }

    }

}

@Composable
fun PreviewItem(
    text: String,
    modifier: Modifier = Modifier,
    onCustomDraw: (DrawScope.() -> Unit)? = null
) {

    Column(
            modifier = modifier.size(MaterialTheme.spacing.spaceMedium * 10),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLabelText(text = text, textStyle = MaterialTheme.typography.labelSmall)

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceExtraSmall))

        DrawingCanvas(
                modifier = Modifier.fillMaxSize(),
                paddingValues = PaddingValues(horizontal = MaterialTheme.spacing.spaceExtraSmall),
                outerRadius = MaterialTheme.spacing.spaceMedium
        ) {
            onCustomDraw?.invoke(this)
        }
    }
}


@PreviewLightDark
@Composable
private fun PreviewContentScreenPreview() {
    ScribbleDashTheme {
        PreviewContentScreen(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                uiState = PreviewUiState(),
                onEvent = {}

        )
    }
}