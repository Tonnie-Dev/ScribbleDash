package com.tonyxlab.scribbledash.presentation.screens.draw

import android.R.attr.text
import android.R.attr.textStyle
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.util.TableInfo
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


@Composable
fun DrawScreen(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DrawViewModel = koinViewModel()
) {

    val state = viewModel.drawingUiState.collectAsStateWithLifecycle().value
    DrawScreenContent(
            modifier = modifier,
            currentPath = state.currentPath,
            paths = state.paths,
            onClose = onClose,
            onAction = viewModel::onEvent
    )

}

@Composable
fun DrawScreenContent(
    currentPath: PathData?,
    paths: List<PathData>,
    onClose: () -> Unit,
    onAction: (DrawingActionEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        AppCloseIcon(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceTwelve * 4),
                onClose = onClose
        )
        AppHeadlineText(
                modifier = modifier.padding(
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
            AppIcon(icon = R.drawable.ic_reply) { }
            AppIcon(icon = R.drawable.ic_forward) { }
            AppButton(
                    modifier = Modifier.height(MaterialTheme.spacing.spaceExtraLarge),
                    contentColor = Success
            ) { }
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


