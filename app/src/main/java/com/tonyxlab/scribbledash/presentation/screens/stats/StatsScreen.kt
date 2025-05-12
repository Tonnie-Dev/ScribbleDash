package com.tonyxlab.scribbledash.presentation.screens.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import com.tonyxlab.scribbledash.presentation.core.components.AppTopBar
import com.tonyxlab.scribbledash.presentation.screens.stats.handling.StatsUiState
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.navigation.NavOperations
import com.tonyxlab.scribbledash.presentation.core.base.BaseContentLayout
import com.tonyxlab.scribbledash.presentation.core.components.AppBottomBar
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.screens.stats.components.StatItem
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme
import com.tonyxlab.scribbledash.presentation.theme.labelXLarge
import org.koin.androidx.compose.koinViewModel

@Composable
fun StatsScreen(
    modifier: Modifier = Modifier,
    navOperations: NavOperations,
    navController: NavController,
    viewModel: StatsViewModel = koinViewModel()
) {

    BaseContentLayout(viewModel = viewModel, bottomBar = { AppBottomBar(navController = navController) }){ statsUiState ->
        StatsScreenContent(modifier = modifier,statsUiState = statsUiState)

    }

}


@Composable
private fun StatsScreenContent(
    modifier: Modifier = Modifier,
    statsUiState: StatsUiState
) {

    Column(modifier = modifier) {
        AppTopBar(
                title = stringResource(id = R.string.header_text_stats),
                style = MaterialTheme.typography.labelXLarge
        )

        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            StatItem(
                    icon = R.drawable.stat_item_hour_glass,
                    itemText = R.string.stat_text_highest_speed_draw,
                    quota = statsUiState.highestSpeedModeAccuracy.toString().plus("%")
            )

            StatItem(
                    icon = R.drawable.stat_item_bolt,
                    itemText = R.string.stat_text_highest_meh,
                    quota = statsUiState.mostMeh.toString()
            )


            StatItem(
                    icon = R.drawable.stat_item_star,
                    itemText = R.string.stat_text_highest_accuracy,
                    quota = statsUiState.highestEndlessModeAccuracy.toString().plus("%")
            )


            StatItem(
                    icon = R.drawable.stat_item_palette,
                    itemText = R.string.stat_text_highest_completed,
                    quota = statsUiState.mostEndlessModeCompletion.toString()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun StatsScreenContentPreview() {

    ScribbleDashTheme{

        StatsScreenContent(statsUiState = StatsUiState())
    }


}