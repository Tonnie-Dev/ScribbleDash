package com.tonyxlab.scribbledash.presentation.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.core.utils.GradientScheme
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    screenText: String = stringResource(R.string.text_empty) ,
) {
    Scaffold(containerColor = MaterialTheme.colorScheme.surface) {
        innerPadding ->

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .drawBehind{
                            drawRect(brush = GradientScheme.backgroundGradient)
                        }
                        .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {

            AppHeadlineText(text = screenText)

        }
    }

}


@PreviewLightDark
@Composable
private fun EmptyScreenPreview() {
    ScribbleDashTheme{

        EmptyScreen()
    }
}