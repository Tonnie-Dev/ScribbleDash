package com.tonyxlab.scribbledash.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.theme.OnBackgroundVar
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme

@Composable
fun AppHeadlineText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
            modifier = modifier,
            text = text,
            style = textStyle,
            color = textColor,
            textAlign = textAlign
    )
}

@Composable
fun AppLabelText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = OnBackgroundVar,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
            modifier = modifier,
            text = text,
            style = textStyle,
            color = textColor,
            textAlign = textAlign
    )
}


@Composable
fun AppBodyText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = OnBackgroundVar,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
            modifier = modifier,
            text = text,
            style = textStyle,
            color = textColor,
            textAlign = textAlign
    )
}

@PreviewLightDark
@Composable
private fun AppHeadlineTextPreview() {

    ScribbleDashTheme {

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {
            AppHeadlineText(
                    text = "ScribbleDash",
                    textStyle = MaterialTheme.typography.headlineMedium
            )
            AppHeadlineText(
                    text = "Start Drawing!",
                    textStyle = MaterialTheme.typography.displayMedium
            )
            AppHeadlineText(
                    text = "Time to draw!",
                    textStyle = MaterialTheme.typography.displayMedium
            )

            AppLabelText(
                    text = "Time to draw!"

            )
            AppBodyText(
                    text = "Select game mode"

            )

        }
    }

}