package com.tonyxlab.scribbledash.presentation.screens.draw.components

import android.R.attr.spacing
import android.text.Layout
import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.core.components.AppCloseIcon
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.theme.Error
import com.tonyxlab.scribbledash.presentation.theme.OnBackground
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

        DrawingsCounter(drawings = drawings)
        AppCloseIcon {}


    }
}


@Composable
private fun DrawingsCounter(
    drawings: Int,
    modifier: Modifier = Modifier
) {

    Box {
        Row(
                modifier
                        .clip(RoundedCornerShape(100.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                        .height(28.dp)
                        .widthIn(min = 60.dp)
                        .align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.Center
        ) {
            Text(
                    modifier = Modifier,
                    text = drawings.toString(),
                    style = MaterialTheme.typography.headlineXSmall.copy(color = OnBackground),
                    textAlign = TextAlign.End
            )

        }
        Spacer(modifier = Modifier.width(16.dp))
        Image(
                painter = painterResource(R.drawable.counter_palette),
                contentDescription = null,
                modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterStart)
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

/*
    Row(
            modifier = Modifier.padding(start = MaterialTheme.spacing.spaceMedium),
            horizontalArrangement = Arrangement.spacedBy(
                    space = MaterialTheme.spacing.spaceTen * 2,
                    alignment = Alignment.CenterHorizontally
            )

    ) {


        Image(

                modifier = Modifier
                        .size(MaterialTheme.spacing.spaceTwelve * 3),
                painter = painterResource(R.drawable.counter_palette),
                contentDescription = null,
        )
        Row(
                modifier
                        .clip(RoundedCornerShape(MaterialTheme.spacing.spaceOneHundred))
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                        .height(MaterialTheme.spacing.spaceExtraSmall * 7)
                        .widthIn(min = MaterialTheme.spacing.spaceTen * 6)
                        .padding(top = 12.dp, bottom = 12.dp, start = 22.dp, end = 12.dp)


        ) {
            Text(
                    modifier = Modifier,
                    text = drawings.toString(),
                    style = MaterialTheme.typography.headlineXSmall.copy(color = OnBackground),
                    textAlign = TextAlign.End
            )

        }


    }*/
