package com.tonyxlab.scribbledash.presentation.screens.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.core.components.AppBodyText
import com.tonyxlab.scribbledash.presentation.core.components.AppButton
import com.tonyxlab.scribbledash.presentation.core.components.AppCloseIcon
import com.tonyxlab.scribbledash.presentation.core.components.AppHeaderText
import com.tonyxlab.scribbledash.presentation.core.components.AppLabelText
import com.tonyxlab.scribbledash.presentation.core.components.DrawingCanvas
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme
import com.tonyxlab.utils.drawRandomVector
import org.koin.androidx.compose.koinViewModel

@Composable
fun PreviewScreen(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    viewModel: PreviewViewModel = koinViewModel()
) {

    val state by viewModel.previewUiState.collectAsStateWithLifecycle()

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->


        PreviewContentScreen(
                modifier = modifier.padding(innerPadding),
                score = "",
                sampleSvgStrings = state.sampleSvgStrings,
                viewPortWidth = state.viewPortWidth,
                viewPortHeight = state.viewPortHeight,
               userPathStrings = state.userPathStrings,
                onClose = onClose
        )
    }

}

@Composable
fun PreviewContentScreen(
    score: String, onClose: () -> Unit,
    sampleSvgStrings: List<String>,
    userPathStrings: List<String>,
    viewPortWidth: Float,
    viewPortHeight: Float,
    modifier: Modifier = Modifier
) {


    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        AppCloseIcon(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceDoubleDp * 42),
                onClose = onClose
        )

        AppHeaderText(text = score, textStyle = MaterialTheme.typography.displayLarge)


        Box(modifier = Modifier.fillMaxWidth()) {

            PreviewItem(
                    modifier = Modifier
                            .graphicsLayer {

                                rotationZ = -10f

                            }
                            .align(Alignment.CenterStart),
                    text = stringResource(id = R.string.text_example)
            ) {

                drawRandomVector(
                        vectorPaths = sampleSvgStrings,
                        viewportWidth = viewPortWidth,
                        viewportHeight = viewPortHeight


                )
            }
            PreviewItem(

                    modifier = Modifier
                            .graphicsLayer {

                                rotationZ = 10f

                            }
                            .align(Alignment.CenterEnd),


                    text = stringResource(id = R.string.text_drawing)
            ){


                drawRandomVector(
                        vectorPaths = userPathStrings,



                )
            }
        }

        AppHeaderText("Woohoo!", textStyle = MaterialTheme.typography.headlineLarge)

        AppBodyText(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceOneHundredFifty),
                text = "kllkkkk"
        )

        AppButton(

                containerColor = MaterialTheme.colorScheme.primary,
                buttonText = stringResource(R.string.button_text_try_again)
        ) { }


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
        DrawingCanvas(modifier = Modifier.fillMaxSize()) {

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
                score = "13%",
                sampleSvgStrings = listOf(),
                userPathStrings = listOf(),
                viewPortWidth = 0f,
                viewPortHeight = 0f,
                onClose = {}
        )
    }
}