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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.core.components.AppButton
import com.tonyxlab.scribbledash.presentation.core.components.AppCloseIcon
import com.tonyxlab.scribbledash.presentation.core.components.AppHeadlineText
import com.tonyxlab.scribbledash.presentation.core.components.AppIcon
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.screens.draw.components.DrawingCanvas
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



Timber.i("Inside Draw Screen")

    val state = viewModel.drawingUiState.collectAsStateWithLifecycle().value


    Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->


        DrawScreenContent(
                modifier = modifier.padding(innerPadding),
                currentPath = state.currentPath,
                paths = state.paths,
                onClose = onClose,
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
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.padding(MaterialTheme.spacing.spaceTen * 3),
            horizontalAlignment = Alignment.CenterHorizontally) {

        AppCloseIcon(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceTwelve * 4),
                onClose = onClose
        )
        AppHeadlineText(
                modifier = Modifier.padding(
                        bottom = MaterialTheme.spacing.spaceTwelve * 4,
                ),
                textStyle = MaterialTheme.typography.displayMedium,
                text = stringResource(R.string.text_time_to_draw)
        )

        DrawingCanvas(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceOneHundredFifty),
                currentPath = currentPath,
                paths = paths,
                onAction = onAction
        )
        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceTwelve)) {
            AppIcon(icon = R.drawable.ic_reply, onClick = {onAction(DrawingActionEvent.OnRedo)})
            AppIcon(icon = R.drawable.ic_forward) { {onAction(DrawingActionEvent.OnRedo)} }
            AppButton(
                    modifier = Modifier.height(MaterialTheme.spacing.spaceExtraLarge),
                    contentColor = Success,
                    onClick = { onAction(DrawingActionEvent.OnClearCanvas)}
            )
        }
    }

}


@PreviewLightDark
@Composable
private fun DrawScreenContentPreview() {

    ScribbleDashTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
        ) {
            DrawScreenContent(onClose = {}, currentPath = null, paths = emptyList(), onAction = {})
        }
    }

}


