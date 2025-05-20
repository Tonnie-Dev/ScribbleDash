package com.tonyxlab.scribbledash.presentation.screens.draw.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.scribbledash.presentation.core.components.AppCloseIcon
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.theme.Error
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme
import com.tonyxlab.scribbledash.presentation.theme.headlineXSmall


@Composable
fun CounterRow(
    timeText: String,
    totalRemainingSecs: Int,
    modifier: Modifier = Modifier
) {

    Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {

        DrawCounter(
                timeText = timeText,
                totalRemainingSecs = totalRemainingSecs
        )
        AppCloseIcon {}

    }
}

@Composable
fun DrawCounter(
    timeText: String,
    totalRemainingSecs: Int,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineXSmall,
) {
    Box(
            modifier = modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .size(MaterialTheme.spacing.spaceTwelve * 4),
            contentAlignment = Alignment.Center
    ) {
        val textColor = if (totalRemainingSecs <= 30)
            Error
        else
            MaterialTheme.colorScheme.onBackground


            Text(
                    text = timeText,
                    style = textStyle.copy(color = textColor)
            )


    }

}

@PreviewLightDark
@Composable
private fun CounterRowPreview() {
    ScribbleDashTheme {

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {
            CounterRow(timeText = "2:00", totalRemainingSecs = 50)
            CounterRow(timeText = "2:00", totalRemainingSecs = 30)
            CounterRow(timeText = "1:00", totalRemainingSecs = 29)

        }


    }
}

