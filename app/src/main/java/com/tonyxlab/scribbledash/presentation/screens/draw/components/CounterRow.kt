package com.tonyxlab.scribbledash.presentation.screens.draw.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.core.components.AppCloseIcon
import com.tonyxlab.scribbledash.presentation.core.components.AppHeaderText
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.theme.Error
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme
import com.tonyxlab.scribbledash.presentation.theme.headlineXSmall

@Composable
fun CounterRow(
    timeText: String,
    totalRemainingSecs: Int,
    drawings: Int,
    modifier: Modifier = Modifier
) {

    Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {

        DrawTimeCounter(
                timeText = timeText,
                totalRemainingSecs = totalRemainingSecs
        )

        DrawingsCounter(count = drawings)

        AppCloseIcon {}

    }
}

@Composable
private fun DrawingsCounter(
    count: Int,
    modifier: Modifier = Modifier
) {
    Box(
            modifier = modifier
                    .width(MaterialTheme.spacing.spaceDoubleDp * 38),
            contentAlignment = Alignment.Center
    ) {
        Row(
                modifier = Modifier
                        .offset(x = MaterialTheme.spacing.spaceMedium)
                        .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(MaterialTheme.spacing.spaceOneHundred)
                        )
                        .width(MaterialTheme.spacing.spaceDoubleDp * 30)
                        .height(MaterialTheme.spacing.spaceDoubleDp * 14)
                        .align(Alignment.CenterStart)
                        .padding(
                                start = MaterialTheme.spacing.spaceDoubleDp * 15,
                                end = MaterialTheme.spacing.spaceTwelve

                        ),
                verticalAlignment = Alignment.CenterVertically

        ) {

            AppHeaderText(
                    text = count.toString(),
                    textStyle = MaterialTheme.typography.headlineXSmall,
            )

        }

        Image(
                modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(MaterialTheme.spacing.spaceTwelve * 3),
                painter = painterResource(R.drawable.counter_palette),
                contentDescription = null
        )

    }


}


@Composable
private fun DrawTimeCounter(
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
            CounterRow(timeText = "2:00", totalRemainingSecs = 50, drawings = 0)
            CounterRow(timeText = "2:00", totalRemainingSecs = 30, drawings = 8)
            CounterRow(timeText = "1:00", totalRemainingSecs = 29, drawings = 30)

        }


    }
}

