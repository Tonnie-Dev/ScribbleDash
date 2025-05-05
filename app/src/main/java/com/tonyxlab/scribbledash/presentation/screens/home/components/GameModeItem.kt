package com.tonyxlab.scribbledash.presentation.screens.home.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.theme.Primary
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme
import com.tonyxlab.scribbledash.presentation.theme.Success
import com.tonyxlab.scribbledash.presentation.theme.TertiaryContainer


@Composable
fun GameModeItem(
    gameMode: GameMode,
    onSelectGameMode: (GameMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
            modifier = modifier
                    .clip(RoundedCornerShape(MaterialTheme.spacing.spaceDoubleDp * 10))
                    .background(color = gameMode.outlineColor)
                    .clickable { onSelectGameMode(gameMode) }
                    .padding(MaterialTheme.spacing.spaceSmall)

    ) {

        Row(
                modifier = Modifier
                        .clip(RoundedCornerShape(MaterialTheme.spacing.spaceDoubleDp * 10))
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(start = MaterialTheme.spacing.spaceDoubleDp * 11),
                verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                    modifier = Modifier.fillMaxWidth(.4f),
                    text = stringResource(gameMode.title),
                    style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                    )
            )
            Image(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(gameMode.image),
                    contentDescription = stringResource(gameMode.title)
            )
        }


    }
}

sealed class GameMode(
    @StringRes val title: Int,
    @DrawableRes val image: Int,
    val outlineColor: Color
) {

    data object OneRoundWonder : GameMode(
            title = R.string.game_mode_one_round_wonder,
            image = R.drawable.game_mode_one_round_wonder,
            outlineColor = Success
    )

    data object SpeedDraw : GameMode(
            title = R.string.game_mode_speed_draw,
            image = R.drawable.game_mode_speed_draw,
            outlineColor = Primary

    )

    data object EndlessMode : GameMode(
            title = R.string.game_mode_endless,
            image = R.drawable.game_mode_endless,
            outlineColor = TertiaryContainer
    )
}

@PreviewLightDark
@Composable
private fun GameModeItemPreview() {
    ScribbleDashTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {
            GameModeItem(gameMode = GameMode.OneRoundWonder, onSelectGameMode = {})
            GameModeItem(gameMode = GameMode.SpeedDraw, onSelectGameMode = {})
            GameModeItem(gameMode = GameMode.EndlessMode, onSelectGameMode = {})
        }
    }
}
