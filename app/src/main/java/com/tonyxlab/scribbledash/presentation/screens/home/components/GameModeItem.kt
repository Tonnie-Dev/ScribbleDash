package com.tonyxlab.scribbledash.presentation.screens.home.components

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme
import com.tonyxlab.scribbledash.presentation.theme.Success


@Composable
fun GameModeItem(
    mode: GameMode,
    onItemClick: (GameMode) -> Unit,
    backgroundColor: Color = Success,
    modifier: Modifier = Modifier
) {
    Box(
            modifier = modifier
                    .clip(RoundedCornerShape(MaterialTheme.spacing.spaceDoubleDp * 10))
                    .background(color = backgroundColor)
                    .clickable { onItemClick(mode) }
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
                    modifier = Modifier.weight(1f),
                    text = mode.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                    )
            )
            Image(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(mode.image),
                    contentDescription = mode.title
            )
        }


    }
}

sealed class GameMode(val title: String, @DrawableRes val image: Int) {

    data object OneRoundWonder : GameMode(
            title = "One Round Wonder",
            image = R.drawable.mode_one_round_wonder
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
                verticalArrangement = Arrangement.Center
        ) {
            GameModeItem(mode = GameMode.OneRoundWonder, onItemClick = {})
        }
    }
}
