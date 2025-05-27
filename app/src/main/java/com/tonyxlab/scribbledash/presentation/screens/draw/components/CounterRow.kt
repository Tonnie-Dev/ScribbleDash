package com.tonyxlab.scribbledash.presentation.screens.draw.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.domain.model.Game
import com.tonyxlab.scribbledash.domain.model.GameMode
import com.tonyxlab.scribbledash.domain.model.formatRemainingSecs
import com.tonyxlab.scribbledash.presentation.core.components.AppCloseIcon
import com.tonyxlab.scribbledash.presentation.core.components.AppHeaderText
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.theme.Error
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme
import com.tonyxlab.scribbledash.presentation.theme.headlineXSmall

@Composable
fun CounterRow(
    game: Game,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box {
        Row(
                modifier = modifier
                        .fillMaxWidth()
                        .height(intrinsicSize = IntrinsicSize.Max)
                        .padding(
                                start = MaterialTheme.spacing.spaceMedium,
                                top = MaterialTheme.spacing.spaceSmall,
                                bottom = MaterialTheme.spacing.spaceSmall
                        ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {

            if (game.mode is GameMode.SpeedDraw) {
                TimeCounter(game = game)
            }

            AppCloseIcon { onClose() }
        }

        if (game.mode is GameMode.SpeedDraw || game.mode is GameMode.EndlessMode) {
            DrawingsCounter(
                    modifier = Modifier.align(Alignment.Center),
                    count = game.drawingsCount
            )
        }
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
                                color = MaterialTheme.colorScheme.surfaceContainerLow,
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
                    modifier = Modifier.fillMaxWidth(),
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
private fun TimeCounter(
    game: Game,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineXSmall,
) {
    Surface(
            modifier = modifier
                    .clip(CircleShape)
                    .shadow(elevation = MaterialTheme.spacing.spaceSmall)


    ) {
        Box(
                modifier = Modifier
                        .clip(CircleShape)

                        .size(MaterialTheme.spacing.spaceDoubleDp * 28)
                        .padding(MaterialTheme.spacing.spaceExtraSmall),
                contentAlignment = Alignment.Center
        ) {
            val textColor = if (game.remainingSecs <= 30)
                Error
            else
                MaterialTheme.colorScheme.onBackground


            Text(
                    text = game.formatRemainingSecs(),
                    style = textStyle.copy(color = textColor)
            )

        }
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

            CounterRow(
                    game = Game(mode = GameMode.OneRoundWonder),
                    onClose = {}
            )

            CounterRow(
                    game = Game(
                            mode = GameMode.SpeedDraw,
                            remainingSecs = 120,
                            drawingsCount = 13
                    ),
                    onClose = {}
            )

            CounterRow(
                    game = Game(
                            mode = GameMode.SpeedDraw,
                            remainingSecs = 70,
                            drawingsCount = 13
                    ),
                    onClose = {}
            )

            CounterRow(
                    game = Game(
                            mode = GameMode.SpeedDraw,
                            remainingSecs = 69,
                            drawingsCount = 13
                    ),
                    onClose = {}
            )
            CounterRow(
                    game = Game(
                            mode = GameMode.SpeedDraw,
                            remainingSecs = 20,
                            drawingsCount = 13
                    ),
                    onClose = {}
            )
            CounterRow(
                    game = Game(
                            mode = GameMode.EndlessMode,
                            drawingsCount = 23
                    ),
                    onClose = {}
            )
        }
    }
}

