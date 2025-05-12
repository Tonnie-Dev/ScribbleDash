package com.tonyxlab.scribbledash.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.domain.model.GameMode
import com.tonyxlab.scribbledash.navigation.NavOperations
import com.tonyxlab.scribbledash.presentation.core.components.AppBodyText
import com.tonyxlab.scribbledash.presentation.core.components.AppBottomBar
import com.tonyxlab.scribbledash.presentation.core.components.AppHeaderText
import com.tonyxlab.scribbledash.presentation.core.utils.GradientScheme
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.screens.home.components.GameModeItem
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme

@Composable
fun HomeScreen(
    navController: NavController,
    navOperations: NavOperations,
    modifier: Modifier = Modifier
) {
    Scaffold(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.surface,
            bottomBar = {
                AppBottomBar(navController = navController)
            }
    ) { innerPadding ->

        HomeScreenContent(
                modifier = modifier.padding(innerPadding),
                onSelectGameMode = {navOperations.navigateToDifficultyScreen()}
        )

    }

}


@Composable
fun HomeScreenContent(
    onSelectGameMode: (GameMode) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
            modifier = modifier
                    .drawBehind {
                        drawRect(brush = GradientScheme.backgroundGradient)
                    }
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.spaceMedium)

    ) {

        AppHeaderText(
                modifier = Modifier.padding(
                        start = MaterialTheme.spacing.spaceMedium,
                        end = MaterialTheme.spacing.spaceMedium,
                        top = MaterialTheme.spacing.spaceTwelve,
                        bottom = MaterialTheme.spacing.spaceTwelve * 8
                ),
                text = stringResource(id = R.string.app_name)
        )

        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                                bottom = MaterialTheme.spacing.spaceDoubleDp * 10
                        ),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceExtraSmall),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {


            AppHeaderText(
                    text = stringResource(id = R.string.button_text_start_drawing),
                    textStyle = MaterialTheme.typography.displayMedium
            )
            AppBodyText(text = stringResource(id = R.string.text_select_game_mode))
        }

        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceTwelve)) {

            GameModeItem(
                    gameMode = GameMode.OneRoundWonder,
                    onSelectGameMode = onSelectGameMode
            )
            GameModeItem(
                    gameMode = GameMode.SpeedDraw,
                    onSelectGameMode = onSelectGameMode
            )

            GameModeItem(
                    gameMode = GameMode.EndlessMode,
                    onSelectGameMode = onSelectGameMode
            )
        }

    }
}


@PreviewLightDark
@Composable
private fun HomeScreenContentPreview() {

    ScribbleDashTheme {

        HomeScreenContent(

                onSelectGameMode = {}
        )
    }

}